import os
import psutil
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json
import requests

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

# Get system information
cputemp = os.popen('vcgencmd measure_temp').readline().replace("temp=","").replace("'C\n","")
cpuusage = "{:.2f}".format(psutil.cpu_percent(interval=10))
ramusage = str(psutil.virtual_memory().percent)
available_storage_bytes = psutil.disk_usage('/').free
available_storage_gb = str(round(available_storage_bytes / (1024 ** 3), 2))

# Get Pi-hole data
pihole_data = requests.get("http://localhost/admin/api.php?summaryRaw&auth=YOUR_API_KEY").json()
ads_percentage_today = str(round(pihole_data['ads_percentage_today'], 2))

# Send data to Firebase
ref = db.reference('')
ref.update({
    'hw': {
        'cputemp': cputemp,
        'cpuusage': cpuusage,
        'ramusage': ramusage,
        'avaiblestorage': available_storage_gb
    },
    'pihole': {
        'domainsbeingblocked': pihole_data['domains_being_blocked'],
        'dnsqueriestoday': pihole_data['dns_queries_today'],
        'adsblockedtoday': pihole_data['ads_blocked_today'],
        'adspercentagetoday': ads_percentage_today
    }
})
