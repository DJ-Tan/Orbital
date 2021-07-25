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
        <li><a href="#activties-and-fragments">Activties and Fragments</a></li>
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

<u>Login Activity</u>
* Uses a email-password login combination
* Basic error checking
* 'Remember Me' option (to be added)

![Login Page](https://user-images.githubusercontent.com/65007534/120193486-137b8880-c24f-11eb-942e-15e22a54df0a.png)

<u>Register Activity</u>
* Sign up page for new users
* Basic check for valid password and email
* Email confirmation upon registration (to be added)

![Register Page](https://user-images.githubusercontent.com/65007534/120193575-2c843980-c24f-11eb-9c95-cbd5371b8f04.png)

<u>Main Activity</u>
* Displays pending dispensing notifications (to be added)
* Side bar menu to navigate to other windows
* Home/Profile/Settings Fragments
* Log-out feature that returns user to login screen

![Sidebar Menu](https://user-images.githubusercontent.com/65007534/123634630-45333f80-d84d-11eb-989f-8875dcdd1ba9.png)

<u>Profile Fragment</u>
* Displays basic information of the user
* To be used under user sharing

![Profile Menu](https://user-images.githubusercontent.com/65007534/123635997-eb337980-d84e-11eb-98d3-abd226a4dd1e.png)

<u>Pill Fragment</u>
* Starts out on a pill storage screen with 6 containers
* CLick on any container to open up the pill edit screen
* Comes with 6 colours and 7 shapes

![Pill Storage](https://user-images.githubusercontent.com/65007534/123635052-c25eb480-d84d-11eb-8eaa-12848cbd61ac.png)
![Pill Edit](https://user-images.githubusercontent.com/65007534/123635105-d9050b80-d84d-11eb-8cc9-7f5ec4d32e03.png)

<u>Setting Fragment</u>
* Change Password

![Setting Fragment](https://user-images.githubusercontent.com/65007534/123635608-72342200-d84e-11eb-855a-4d573cf818f3.png)


<u>Features yet to be implemented</u>
* Ability to make and update basic profile settings
* Enable/Disable notifications
* Scheduler
* Password change
* Sharing ID to allow family members and doctor's access
* Feedback system
