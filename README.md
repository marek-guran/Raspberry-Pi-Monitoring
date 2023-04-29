# What is this?
This is my small project which basically does this:

Python program running on Raspberry Pi is sending data about CPU (usage, temperature), RAM (usage), SD Card (free storage) and Pi Hole data (Latest 24 hours, basically what is on dashboard when you open web interface) to Firebase from which is downloading these data the android app.

I am planning to add NAS monitoring, for now it is marked as TBA in android app.

# How do I get started to make the app work?
1. Create firebase project with real time database
2. In firebase open project settings - Service Accounts - Firebase Admin SDK - Select python - and click blue button to download json
3. Put this json into folder where you have stored the python file
4. Open python file and change this line: cred = credentials.Certificate("/home/pi/python/firebase_credential.json") with your own location where you have stored the json you downloaded
5. Go back to Service Accounts, open database secrets and copy and paste secret into apiKey = "YOUR DATABASE SECRET"
6. Open your firebase database and copy reference url (that one that has icon to copy next to it) and put it in databaseURL = "YOUR DATABASE URL"
7. Open Pi hole web interface, go to settings - API - show api token and paste this token inside of this line after auth=...: pihole_data = requests.get("http://localhost/admin/api.php?summaryRaw&auth=YOUR_API_KEY").json()
8. Save and try running it, if your firebase database gets data, it works and we can move to android app
9. Download android studio
10. Open android studio and open in it folder with android app project
11. Rename texts to your language, for now you will need to open layout by layout since i did not yet implement strings
12. In android studio select in top bar: Tools - Firebase - Realtime Database - Get started KOTLIN - login, pick your project and it should be done
13. To create android app you have 2 options: 1. On top bar there is Build - build bunde/apk - build apk, once this is done you click locate on pop up menu that will show up in bottom right corner and manually install this app. 2. Second option is opening developer options, enable usb debugging and plugging in phone into PC, on phone enable pop up if you get one and in android studio click green arrow Run app and you are done

NOTE: The python script is meant to be run on device that has in it running Pi Hole, you will need to modify the localhost to IP on your local network with pi hole if you are not gonna run it on that particular device.

# Current state images
For now the color scheme and design choice is... well not the best choice but works for now 😅 App has dark and light color scheme. For now it is based only by device settings.
<details> 
   <summary>Show</summary> 
<img src="https://user-images.githubusercontent.com/26904790/235303862-2d6c2898-2efb-43bb-8952-deb4ec70cd44.png" width = "200px"> <img src="https://user-images.githubusercontent.com/26904790/235303866-58e39241-9350-4ea9-bc94-1f01f6308452.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235303868-f820b8dd-1f22-4f4b-9146-47a2014d896a.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235303870-0837cf94-37e7-4ce2-b4d0-6d75d6e97ebb.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235303872-f7baf8e7-d4e7-49d9-bda1-361b8ceb1bef.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235303873-59ff1bce-1bab-43ea-b961-3a7aeb2ac0ea.png" width = "200px">
   </details>



# Can I help?
Yes! You can help with development of the script or android app. Every change will be reviewed and decided if it will make to latest build.
