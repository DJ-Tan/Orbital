<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#project-details">Project Details</a>
      <ul>
        <li><a href="#dependencies">Dependencies</a></li>
        <li>
          <a href="#activties-and-fragments">Activties and Fragments</a>
          <ul>
            <li><a href="#login-activity">Login Activity</a></li>
            <li><a href="#register-activity">Register Activity</a></li>
            <li><a href="#main-activity">Main Activity</a></li>
            <li><a href="#profile-fragment">Profile Fragment</a></li>
            <li><a href="#pill-fragment">Pill Fragment</a></li>
            <li><a href="#schedule-fragment">Schedule Fragment</a></li>
            <li><a href="#settings-fragment">Setting Fragment</a></li>
          </ul>
        </li>
      </ul>
    </li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

The project details an android martphone application to be used in conjunction with an automated pill dispensary for alert and tracking purposes.


### Built With

* Android Studio


<!-- CODE DETAILS -->
## Project Details

The following describes the basic features that have been or are to be added.

### Dependencies

The following dependencies are to be imported onto Android Studio on top of the existing ones:
* implementation 'androidx.cardview:cardview:1.0.0'
* implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
* implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
* implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'

The purpose of each dependency is as follows:
* allows for displays to be stacked on top of each other
* enable communication of information between the android application and the MySQL server
* allows for navigation between activties, fragments or other components


### Activities and Fragments

#### Login Activity
* Uses a email-password login combination
* Basic error checking

![Login Page](https://user-images.githubusercontent.com/65007534/126902499-78cebc9e-0b32-4bec-9abd-8ff78f13541d.png)

#### Register Activity
* Sign up page for new users
* Basic check for valid password and email

![Register Page](https://user-images.githubusercontent.com/65007534/126902571-d8f6be59-b7ae-4925-81ca-b7391d76d10f.png)

#### Main Activity
* Side bar menu to navigate to other windows
* Home/Profile/Pill/Schedule/Settings Fragments
* Log-out feature that returns user to login screen

![Sidebar Menu](https://user-images.githubusercontent.com/65007534/126902616-32c2cd5a-89e1-476c-bb22-2bf4943642ca.png)

#### Profile Fragment
* Displays basic information of the user
* To be used under user sharing

![Profile Page](https://user-images.githubusercontent.com/65007534/126902650-59c30e8b-a3ad-4dc8-b662-f5f929a1bd9f.png)

#### Pill Fragment
* Starts out on a pill storage screen with 6 containers
* CLick on any container to open up the pill edit screen
* Comes with 6 colours and 7 shapes
* Clear container option

![Pill Storage](https://user-images.githubusercontent.com/65007534/126902700-36558206-c1d2-41bb-ae3e-d7548d15eb7a.png)
![Pill Edit](https://user-images.githubusercontent.com/65007534/126902703-7afad576-dae9-4a25-a7dd-4b7c4898aa7e.png)
 
#### Schedule Fragment
* Schedule dispense day, quantity and time for the pills
* Caters for both flexible and non-flexible time (see settings)

![Pill Scheduler](https://user-images.githubusercontent.com/65007534/126902769-2fd23324-c0fa-40d0-923b-7bdc2198efac.png)

#### Settings Fragment
* Change password
* Change time flexibility

![Settings Page](https://user-images.githubusercontent.com/65007534/126902785-4ede6210-cf4b-45f1-8ed7-2779256bd3ac.png)


<u>Future Improvements</u>
* "Remember Me" option for ease of login
* Registration confirmation by email address
* Important notifications on home screen (exp: Pills running low, expiring pills)
* User sharing to allow family members and doctor's access to base level information
* Feedback system
