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
gpu_temp = os.popen('vcgencmd measure_temp').readline().replace("temp=","").replace("'C\n","")
cpu_voltage = os.popen('vcgencmd measure_volts').readline().replace("volt=","").replace("V\n","")
cpu_clock_freq = str(int(os.popen('vcgencmd measure_clock arm').readline().replace("frequency(48)=","").strip()) // 1000000)
gpu_clock_freq = str(int(os.popen('vcgencmd measure_clock core').readline().replace("frequency(1)=","").strip()) // 1000000)

# Get Raspberry Pi model information
model = os.popen('cat /proc/device-tree/model').readline().strip()

# Get Pi-hole data
pihole_data = requests.get("http://localhost/admin/api.php?summaryRaw&auth=YOUR_API_KEY").json()
ads_percentage_today = str(round(pihole_data['ads_percentage_today'], 2))

# Send data to Firebase
ref = db.reference('')
ref.update({
    'hw': {
        'model': model,
        'cputemp': cputemp,
        'cpuusage': cpuusage,
        'ramusage': ramusage,
        'avaiblestorage': available_storage_gb,
        'gpu_temp': gpu_temp,
        'cpu_voltage': cpu_voltage,
        'cpu_clock_freq': cpu_clock_freq,
        'gpu_clock_freq': gpu_clock_freq
    },
    'pihole': {
        'domainsbeingblocked': str(pihole_data['domains_being_blocked']),
        'dnsqueriestoday': str(pihole_data['dns_queries_today']),
        'adsblockedtoday': str(pihole_data['ads_blocked_today']),
        'adspercentagetoday': str(ads_percentage_today)
    }
})
