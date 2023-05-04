# What is this?
This is my small project which basically does this:

Python program running on Raspberry Pi is sending data about CPU, GPU, RAM, SD Card,model of your Raspberry Pi and Pi Hole data (Latest 24 hours, basically what is on dashboard when you open web interface) to Firebase from which is downloading these data the android app. You can also reboot, shutdown your device or update AdsLists (Gravity), reboot PiHole without need to be connected on local network or VPN.

# But why?
I don't like opening my network to outside world... This way I can monitor everything I need and also hardware stats with just one single app no matter where I am.

# Install python packages:
```
sudo pip3 install psutil firebase_admin requests (if you are gonna use management.py)
pip3 install psutil firebase_admin requests (if you are not gonna use management.py)
```
The sudo version needs to install these packages for sudo user so it can be executed on each boot as sudo since it reboots, shutdowns Raspberry Pi, restarts PiHole service and updates gravity list.

# How do I get started to make the app work?
1. Create firebase project with real time database
2. In firebase open project settings - Service Accounts - Firebase Admin SDK - Select python - and click blue button to download json
3. Put this json into folder where you have stored the python file
4. Open python file and change this line: cred = credentials.Certificate("/home/pi/python/firebase_credential.json") with your own location where you have stored the json you downloaded
5. Go back to Service Accounts, open database secrets and copy and paste secret into apiKey = "YOUR DATABASE SECRET"
6. Open your firebase database and copy reference url (that one that has icon to copy next to it) and put it in databaseURL = "YOUR DATABASE URL"
7. Open Pi hole web interface, go to settings - API - show api token and paste this token inside of this line after auth=...: pihole_data = requests.get("http://localhost/admin/api.php?summaryRaw&auth=YOUR_API_KEY").json()
8. Save and try running it, if your firebase database gets data, it works and we can move to android app (if you will want to execute the python code automatically every few minutes and on boot, I recommend you doing that by cron job) Example how to setup cron job: Open terminal and execute command: ```crontab -e``` (pick your editor) and put at the end of file (put your location of script instead of mine): 
```
@reboot python3 /home/pi/python/monitoring.py
* * * * * python3 /home/pi/python/monitoring.py
``` 
This will make python script start on boot and and 5 * will make it start automatically each 1 minute. If you chose nano, hit ```ctrl + x```, click ```y``` and then lastly ```enter```

9. Download android studio
10. Open android studio and open in it folder with android app project
11. Rename texts to your language, you will need to add in values your language or just edit main strings file (if you create new file for your language, feel free to contribute it for everyone to use)
12. In android studio select in top bar: Tools - Firebase - Realtime Database - Get started KOTLIN - login, pick your project and it should be done
13. To create android app you have 2 options: 1. On top bar there is Build - build bunde/apk - build apk, once this is done you click locate on pop up menu that will show up in bottom right corner and manually install this app. 2. Second option is opening developer options, enable usb debugging and plugging in phone into PC, on phone enable pop up if you get one and in android studio click green arrow Run app and you are done

NOTE: The python script is meant to be run on device that has in it running Pi Hole, you will need to modify the localhost to IP on your local network with pi hole if you are not gonna run it on that particular device.

Warning: Model of your Raspberry Pi device in python program willl work only if you are using Raspbian OS or other Raspberry Pi OS distribution. Otherwise python program will be crashing, you can remove this line and put there manually your model or just anything you want.

# If you are gonna use management.py
1. As previus edit the python program and put there your firebase url and database secret
2. Run in terminal command: ```sudo crontab -e``` (You will not see there your changes for monitoring.py as it does not run as SUDO, it will still work as before)
3. Add this line at the end of file: ```@reboot sleep 30 && sudo python3 /home/pi/python/management.py >> /home/pi/python/management.log 2>&1``` (This will make it run as sudo user on each boot and start after 30 seconds to ensure that everything is booted up correctly, the >> means that it will save logs into folder with script just in case it wont work. So if it wont update for you firebase database or execute correctly commands, you will be able to see by nano or any other text editor what went wrong)
4. Reboot Raspberry Pi by ```sudo reboot```
5. Check if it works (it should create management node in your firebase database if you dont have it). If it did not work check errors by nano management.log

# Current state images
For now the color scheme and design choice is... well not the best choice but works for now 😅
<details> 
   <summary>Show</summary> 
<img src="https://user-images.githubusercontent.com/26904790/235688440-0a184989-d3a9-45c3-88ed-04c264356b0d.png" width = "200px"> <img src="https://user-images.githubusercontent.com/26904790/235688448-b684ff3f-7502-4bfc-8d58-f79c1071611e.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235688450-6ce5be56-3e7a-4020-9b42-a12fb086c73a.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235688452-98f2a1a1-6481-4813-b3bd-e40bdb9acf4b.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235688455-8d5ee0ef-74aa-49b7-86bb-e61112c8bf6d.png" width = "200px">
<img src="https://user-images.githubusercontent.com/26904790/235688457-14c695ee-9ded-4025-8edb-7ae1d38a5609.png" width = "200px">
   </details>

# Can I help?
Yes! You can help with development of the script or android app. Every change will be reviewed and decided if it will make to latest build.

## Contributors

[![contributors](https://contrib.rocks/image?repo=marek-guran/Raspberry-Pi-Monitoring)](https://github.com/marek-guran/Raspberry-Pi-Monitoring/graphs/contributors)

---
This repository is not sponsored, endorsed or affiliated with the Raspberry Pi Foundation. The use of the Raspberry Pi name and logo in this repository is done under the Raspberry Pi trademark policy.
