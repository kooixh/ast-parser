app_name = 'ast-parser'
app_ver = '0.0.1'
jar_path = 'target/'$(app_name)'-'$(app_ver)'-SNAPSHOT.jar'


build:
	@ echo "Building Java Appp"
	@ mvn clean package

run:
	@ echo "Running application"
	@ java -jar $(jar_path)