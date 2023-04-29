# What is this?
This is my small project which basically does this:

Python program running on Raspberry Pi is sending data about CPU (usage, temperature), RAM (usage), SD Card (free storage) and Pi Hole data (Latest 24 hours, basically what is on dashboard when you open web interface) to Firebase from which is downloading these data the android app.

I am planning to add NAS monitoring, for now it is marked as TBA in android app.

# How do I get started to make the app work?
1. Create firebase project with real time database
2. In firebase open project settings - Service Accounts - Firebase Admin SDK - Select python - and click blue button to download json
3. Put this json into folder where you have stored the python file
4. Open python file (dont know why did i name it hw.py but it is what it is) and change this line: cred = credentials.Certificate("/home/pi/python/firebase_credential.json") with your own location where you have stored the json you downloaded
5. Go back to Service Accounts, open database secrets and copy and paste secret into apiKey = "YOUR DATABASE SECRET"
6. Open your firebase database and copy reference url (that one that has icon to copy next to it) and put it in databaseURL = "YOUR DATABASE URL"
7. Open Pi hole web interface, go to settings - API - show api token and paste this token inside of this line after auth=...: pihole_data = requests.get("http://localhost/admin/api.php?summaryRaw&auth=YOUR_API_KEY").json()
8. Save and try running it, if your firebase database gets data, it works and we can move to android app
9. Download android studio
10. Open android studio and open in it folder with android app project
11. Rename texts to your language, for now you will need to open layout by layout since i did not yet implement strings
12. In android studio select in top bar: Tools - Firebase - Realtime Database - Get started KOTLIN - login, pick your project and it should be done
13. To create android app you have 2 options: 1. On top bar there is Build - build bunde/apk - build apk, once this is done you click locate on pop up menu that will show up in bottom right corner and manually install this app. 2. Second option is opening developer options, enable usb debugging and plugging in phone into PC, on phone enable pop up if you get one and in android studio click green arrow Run app and you are done

# Current state images
TBA

# Can I help?
Yes! You can help with development of the script or android app. Every change will be reviewed and decided if it will make to latest build.
