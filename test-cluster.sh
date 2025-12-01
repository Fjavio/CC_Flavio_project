#!/bin/bash

#URL Core Service
BASE_URL="http://localhost:8081/api"
CONTENT_TYPE="Content-Type: application/json"

echo "=================================================="
echo " STARTING CLUSTER AND TEST END-TO-END "
echo "=================================================="

#Start with clean data
docker compose down -v 
docker compose up -d --build

echo "Wait start services (40 seconds)..."
sleep 40

#Check exit result
check_status() {
  if [ "$1" -eq 200 ] || [ "$1" -eq 201 ]; then
    echo "✅ $2 - OK (Status: $1)"
  else
    echo "❌ $2 - FAILED (Status: $1)"
    echo "   Log Core Service:"
    docker compose logs core-service
    exit 1
  fi
}

echo "--------------------------------------------------"
echo " START TEST "
echo "--------------------------------------------------"

#CREATE TEACHER-
echo "Creating teacher..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/segreteria/docenti" \
  -H "$CONTENT_TYPE" \
  -d '{ "teacherName": "Prof", "teacherSurname": "Docker", "teacherID": "DOCK001" }')
check_status $STATUS "Create teacher"

#CREATE COURSE
echo "Creating course..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/segreteria/corsi" \
  -H "$CONTENT_TYPE" \
  -d '{ "courseCode": "DK101", "courseName": "Docker Advanced", "cfu": 6, "preOf": "", "preFor": "" }')
check_status $STATUS "Create course"

#ASSOCIATE
echo "Associating course and teacher..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$BASE_URL/segreteria/corsi/DK101/docente" \
  -H "$CONTENT_TYPE" \
  -d '{ "teacherID": "DOCK001" }')
check_status $STATUS "Associate"

#CREATE STUDENT
echo "Creating student..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/segreteria/studenti" \
  -H "$CONTENT_TYPE" \
  -d '{ "username": "student_docker", "password": "123", "pin": 9999999, "idCds": 101 }')
check_status $STATUS "Create student"

#OPEN REPORT (Test integration with ROOM BOOKING SERVICE) ---
#If Room Booking (8083) doesn't respond, it fails
echo "Opening report booking room..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/docente/verbali" \
  -H "$CONTENT_TYPE" \
  -d '{ 
    "reportCode": "VR999", 
    "reportDate": "2025-12-01", 
    "teacherID": "DOCK001",
    "roomName": "Room Docker",
    "timeSlot": "Morning" 
  }')
check_status $STATUS "Opening report"

#ADD EXAM
echo "Adding exam..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/docente/verbali/VR999/esami" \
  -H "$CONTENT_TYPE" \
  -d '{ 
    "vote": 30, 
    "honors": true, 
    "notes": "Very good", 
    "courseCode": "DK101", 
    "username": "student_docker" 
  }')
check_status $STATUS "Add exam"

#CLOSE REPORT (Test integration with NOTIFICATION SERVICE) ---
#If Notification (8082) is unreachable, the system should still return 200 (because it's asynchronous/non-blocking).
echo "Closing report and send notification..."
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/docente/verbali/VR999/chiusura" \
  -H "$CONTENT_TYPE" \
  -d '{ 
    "studentPins": [ { "username": "student_docker", "pin": 9999999 } ] 
  }')
check_status $STATUS "Closing report"

echo "=================================================="
echo " ALL TESTS COMPLETED SUCCESSFULLY"
echo "=================================================="

#Shutdown
docker compose down -v
exit 0