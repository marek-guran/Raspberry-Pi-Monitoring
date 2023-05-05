import os
import psutil
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json
import requests

##########################
### HAS TO RUN AS SUDO ###
##########################

# Firebase configuration
apiKey = "YOUR DATABASE SECRET"
databaseURL = "YOUR DATABASE URL"
firebase_config = {
    "apiKey": apiKey,
    "databaseURL": databaseURL
}

# Initialize Firebase
cred = credentials.Certificate("/home/pi/python/firebase_credential.json")
firebase_admin.initialize_app(cred, firebase_config)


# Get database references
db_ref = db.reference()
management_ref = db_ref.child('management')
reboot_ref = management_ref.child('reboot')
shutdown_ref = management_ref.child('shutdown')
restart_pihole_ref = management_ref.child('restart_pihole')
update_gravity_ref = management_ref.child('update_gravity')

# Check if the management node exists and create it if it doesn't
if not management_ref.get():
    management_ref.set({
        'reboot': '0',
        'shutdown': '0',
        'restart_pihole': '0',
        'update_gravity': '0'
    })

def on_shutdown_change(event):
    shutdown = event.data
    if shutdown == '1':
        print('Shutdown initiated...')
        # Set value to 0 to avoid repeated execution
        shutdown_ref.set('0')
        os.system('sudo shutdown -h now')

def on_reboot_change(event):
    reboot = event.data
    if reboot == '1':
        print('Reboot initiated...')
        # Set value to 0 to avoid repeated execution
        reboot_ref.set('0')
        os.system('sudo reboot')

def on_restart_pihole_change(event):
    restart_pihole = event.data
    if restart_pihole == '1':
        print('Restarting Pi-hole service...')
        # Set value to 0 to avoid repeated execution
        restart_pihole_ref.set('0')
        os.system('sudo docker restart pihole')

def on_update_gravity_change(event):
    update_gravity = event.data
    if update_gravity == '1':
        print('Updating Pi-hole gravity...')
        # Set value to 0 to avoid repeated execution
        update_gravity_ref.set('0')
        os.system('sudo docker exec pihole pihole updateGravity')

# Listen for changes in the management node
shutdown_listener = shutdown_ref.listen(on_shutdown_change)
reboot_listener = reboot_ref.listen(on_reboot_change)
restart_pihole_listener = restart_pihole_ref.listen(on_restart_pihole_change)
update_gravity_listener = update_gravity_ref.listen(on_update_gravity_change)
