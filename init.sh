#!/bin/bash
ES_INDEX=usermanagement
echo admin/admin
curl -XPUT "http://localhost:9200/${ES_INDEX}/user/admin" -d '{    "id" : "admin", "password" : "$2a$10$Q5ljCM5fHGf6OEa.6cy97O/yfSJ.wyMeGjw/lJIWbbrGawi3d4Afa", "roles" : ["ROLE_ADMIN"] }'