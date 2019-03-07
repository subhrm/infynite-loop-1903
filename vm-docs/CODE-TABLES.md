# VMS - CODE TABLES

## This documnet lists few code tables used by Visitor Mangement Services

### 1 - `visitor_status`
>  This table contains various status codes of the visistor, representing their whereabouts

| status_code | description       |
| ----------- | ----------------- |
| 0           | OFF CAMPUS        |
| 1           | AT MAIN GATE      |
| 2           | INSIDE CAMPUS     |
| 3           | INSIDE A BUILDING |
| 4           | IN FOOD COURT     |

### 2- `security_locations`

> This table contains various location codes

| location_code | description         |
| ------------- | ------------------- |
| 1             | BBSR STP GATE#1     |
| 2             | BBSR STP GATE#2     |
| 3             | BBSR STP GATE#3     |
| 101           | BBSR STP Building#1 |
| 102           | BBSR STP Building#2 |
| 103           | BBSR STP Building#3 |
| 201           | BBSR STP FC#1       |


### 3- `event_codes`

> This table contains various Event codes. This event codes should be logged in `visitor_movement_log` table against a `security_location_code`

| event_type | event_name |
| ---------- | ---------- |
| 0          | UNKNOWN    |
| 1          | ENTRY      |
| 2          | EXIT       |

### 4 - `visitor_type`

> This table contains various Types of visitors. Their access is maintained in the table `visitor_access` against a set of `security_location_code`s

| visitor_type_cd | visitor_type_desc                  |
| --------------- | ---------------------------------- |
| B1GUEST         | Can Visit Only Building 1          |
| B2GUEST         | Can Visist Only Building 2         |
| B3GUEST         | Can Visist Only Building 3         |
| EMPLOYEE        | Employee, Can visist all buildings |
| GEN             | General Visitor.Restricted Access  |
