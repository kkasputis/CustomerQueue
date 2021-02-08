# CustomerQueue

This is a customer queue system. 

If you want to pull this app and run on your server and want to use email services you will have to enter your email data in application.properties file and update SERVICE_MAIL in MailService. Sorry, I don't want to share email password :)

When the app is launched admin user is created. You can add and remove specialists with admin via admin page. 

ADMIN credentials: 

username: admin

password: 123


When creating a new SPECIALIST you need to provide not only his username, but his name, that will be visible to customers. Also, you need to provide his working hours and usual service time in minutes for system to be able claculte ticket times.

Anyone can book a ticket and see tickets information(specialist name, ticket code, time of meeting, time left or if specialist is ready to see him) providing a ticket code. Ajax checks if specialist is ready to see customer every 5 seconds.

Any registered user can see "Display Board" which shows active meetings and 5 upcoming meetings. Board refreshes information every 5 seconds using ajax. Also, any registered user can change password in "Profile" section.

If you are logged in as USER you can also see all your tickets in "Profile" section.


If you are logged in as USER you do not need to provide email to cancel your ticket in "Ticket" page. Everyone else, for security reasons, needs to provide matching email to cancel ticket.

If your are logged in as a SPECIALIST you can see "Management" page. From management page SPECIALIST can see all meetings with him, he can start a meeting, stop it or cancel meetings.
If specialist cancels a meeting, user will be notified about it by email. Also, user will receive an email when specialist starts a meeting.

If USER forgot his password, he can ask for a new password in "Login" page. Application will generate new password and send it to his registered email.

Applying for a ticket: If you are logged in as a USER, just choose specialist from a drop-down selection and press "Get ticket". The system will use your registered email. 
Otherwise you will have to provide an email. If you have, or will register an account later with provided email, you will be able to see it in your profile page.

Ticket time: all specialists have their working hours, so you can not get a meeting with them when they are not working. System checks ticket queues for selected specialist and selects nearest available time. System also checks if there were canceled tickets.

