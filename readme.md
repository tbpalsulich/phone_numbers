Grab Phone Numbers
==================
Get a list of all of the phone numbers from all of the files in a directory using Apache Tika.

How to run:

1. Grab this code with `git clone https://github.com/tpalsulich/phone_numbers.git`.
2. Compile by running `mvn compile`.
3. Recursively search a directory by running
`mvn exec:java -Dexec.mainClass="GrabPhoneNumbers" -Dexec.args="/path/to/directory"`