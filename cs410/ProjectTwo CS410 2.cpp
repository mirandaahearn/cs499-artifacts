#include <iostream>
#include <string>
#include <limits>

using namespace std;

// SECURITY VULNERABILITY: Password is hardcoded in the program.
// In a real application this should be stored securely (ex: hashed in a database).
string username = "123";

// SECURITY VULNERABILITY: Global variables can be modified by multiple functions.
// Limiting variable scope would make the program safer.
int customerChoice = 0;

int CheckUserPermissionAccess()
{
    string input;
    int result = 0;

    cout << "Enter password: ";

    // SECURITY VULNERABILITY: Password is entered in plain text.
    // More secure programs hide password input and use encrypted storage.
    cin >> input;

    // SECURITY VULNERABILITY: Password comparison is done in plain text.
    if (input == username)
    {
        result = 1;
    }
    else
    {
        result = 2;
    }

    return result;
}

void DisplayInfo()
{
    // SECURITY VULNERABILITY: Displaying username may reveal internal information.
    cout << "Customer username: " << username << endl;
    cout << "Current customer choice: " << customerChoice << endl;
    cout << "Displaying customer information..." << endl;
}

void ChangeCustomerChoice()
{
    cout << "Choose a customer option:" << endl;
    cout << "1. Option 1" << endl;
    cout << "2. Option 2" << endl;
    cout << "3. Option 3" << endl;
    cout << "4. Option 4" << endl;
    cout << "5. Option 5" << endl;

    // SECURITY VULNERABILITY: Input is not validated here.
    cin >> customerChoice;

    if (customerChoice == 1)
    {
        customerChoice = 1;
    }
    else if (customerChoice == 2)
    {
        customerChoice = 2;
    }
    else if (customerChoice == 3)
    {
        customerChoice = 3;
    }
    else if (customerChoice == 4)
    {
        customerChoice = 4;
    }
    else if (customerChoice == 5)
    {
        customerChoice = 5;
    }
}

int main()
{
    cout << "Created by Miranda Ahearn" << endl;

    int accessResult = 0;

    // SECURITY FIX: Limit login attempts to reduce brute-force attacks
    int attempts = 0;
    const int maxAttempts = 3;

    while (attempts < maxAttempts)
    {
        accessResult = CheckUserPermissionAccess();

        if (accessResult == 1)
        {
            break;
        }

        attempts++;
        cout << "Access denied. Try again." << endl;
    }

    // SECURITY FIX: Stop program after too many failed attempts
    if (accessResult != 1)
    {
        cout << "Too many failed login attempts. Program ending." << endl;
        return 1;
    }

    cout << "Access granted." << endl;

    int menuChoice = 0;

    do
    {
        cout << endl;
        cout << "Menu:" << endl;
        cout << "1. Display Information" << endl;
        cout << "2. Change Customer Choice" << endl;
        cout << "3. Exit" << endl;
        cout << "Enter your choice: ";

        // SECURITY FIX: Validate input so letters or symbols don't break the program
        while (!(cin >> menuChoice))
        {
            cout << "Invalid input. Please enter a number from 1 to 3: ";
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }

        if (menuChoice == 1)
        {
            DisplayInfo();
        }
        else if (menuChoice == 2)
        {
            ChangeCustomerChoice();
        }
        else if (menuChoice == 3)
        {
            cout << "Exiting program." << endl;
        }
        else
        {
            // SECURITY FIX: Prevent invalid menu choices
            cout << "Invalid menu choice. Please select 1, 2, or 3." << endl;
        }

    } while (menuChoice != 3);

    return 0;
}