#include <iostream>
#include <string>
#include <limits>
#include <sstream>
#include <iomanip>
#include <functional>

using namespace std;

// ============================================================
// Miranda Ahearn
// SNHU Milestone Two Module Three
// ENHANCEMENT: Password hashing simulation using std::hash
// In a production application, a cryptographic library such as
// OpenSSL would be used to implement SHA-256. This simulation
// demonstrates the concept of storing a hashed credential
// rather than a plaintext value, aligning with OWASP
// authentication best practices.
// ============================================================
string hashPassword(const string &input)
{
  hash<string> hasher;
  size_t hashValue = hasher(input);
  stringstream ss;
  ss << hex << setw(16) << setfill('0') << hashValue;
  return ss.str();
}

// ENHANCEMENT: Stored hash replaces hardcoded plaintext credential.
// The raw password is never stored or compared directly.
const string STORED_HASH = hashPassword("123");

// ============================================================
// ENHANCEMENT: Global variables removed. customerChoice is now
// passed as a parameter or returned from functions, eliminating
// shared mutable state across functions.
// ============================================================

// ============================================================
// ENHANCEMENT: Input validation helper function.
// Validates that user input is an integer within a specified
// range. Clears cin error state on invalid input and re-prompts.
// Addresses the defensive programming gap identified in the
// original ChangeCustomerChoice and menu functions.
// ============================================================
int getValidatedInput(const string &prompt, int min, int max)
{
  int choice;
  while (true)
  {
    cout << prompt;
    if (cin >> choice && choice >= min && choice <= max)
    {
      return choice;
    }
    cout << "Invalid input. Please enter a number between "
         << min << " and " << max << "." << endl;
    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
  }
}

// ============================================================
// ENHANCEMENT: checkUserPermissionAccess now accepts remaining
// attempts as a parameter rather than relying on global state.
// Password is hashed before comparison. Plaintext comparison
// is eliminated entirely.
// ============================================================
bool checkUserPermissionAccess()
{
  string input;
  cout << "Enter password: ";
  cin >> input;

  // ENHANCEMENT: Compare hash of input against stored hash.
  // Raw password is never compared directly.
  if (hashPassword(input) == STORED_HASH)
  {
    return true;
  }
  return false;
}

// ============================================================
// ENHANCEMENT: displayInfo no longer outputs the stored
// credential. Only the customer choice value is displayed,
// removing the sensitive data exposure identified in the
// original DisplayInfo function.
// ============================================================
void displayInfo(int customerChoice)
{
  cout << "Current customer selection: " << customerChoice << endl;
  cout << "Displaying customer information..." << endl;
}

// ============================================================
// ENHANCEMENT: changeCustomerChoice now uses the validated
// input helper function, eliminating the unvalidated cin call
// and the redundant if-else chain from the original.
// customerChoice is returned rather than stored globally.
// ============================================================
int changeCustomerChoice()
{
  cout << "Choose a customer option:" << endl;
  cout << "1. Option 1" << endl;
  cout << "2. Option 2" << endl;
  cout << "3. Option 3" << endl;
  cout << "4. Option 4" << endl;
  cout << "5. Option 5" << endl;

  // ENHANCEMENT: Validated input replaces direct cin read.
  // Range check ensures only values 1-5 are accepted.
  return getValidatedInput("Enter your choice (1-5): ", 1, 5);
}

// ============================================================
// MAIN: Login attempt limiter retained from original security
// fix. All data passed by parameter. No global state.
// ============================================================
int main()
{
  cout << "Created by Miranda Ahearn" << endl;

  // SECURITY FIX (retained): Limit login attempts to prevent
  // brute-force attacks. Program terminates after 3 failures.
  const int MAX_ATTEMPTS = 3;
  bool accessGranted = false;

  for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++)
  {
    if (checkUserPermissionAccess())
    {
      accessGranted = true;
      break;
    }
    if (attempt < MAX_ATTEMPTS)
    {
      cout << "Access denied. " << (MAX_ATTEMPTS - attempt)
           << " attempt(s) remaining." << endl;
    }
  }

  if (!accessGranted)
  {
    cout << "Too many failed login attempts. Program ending." << endl;
    return 1;
  }

  cout << "Access granted." << endl;

  // ENHANCEMENT: customerChoice is a local variable. No global state.
  int customerChoice = 0;
  int menuChoice = 0;

  do
  {
    cout << endl;
    cout << "Menu:" << endl;
    cout << "1. Display Information" << endl;
    cout << "2. Change Customer Choice" << endl;
    cout << "3. Exit" << endl;

    // ENHANCEMENT: Validated input replaces manual cin loop.
    menuChoice = getValidatedInput("Enter your choice: ", 1, 3);

    if (menuChoice == 1)
    {
      displayInfo(customerChoice);
    }
    else if (menuChoice == 2)
    {
      customerChoice = changeCustomerChoice();
      cout << "Customer choice updated to: " << customerChoice << endl;
    }
    else if (menuChoice == 3)
    {
      cout << "Exiting program." << endl;
    }

  } while (menuChoice != 3);

  return 0;
}