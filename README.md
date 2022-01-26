[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6818157&assignment_repo_type=AssignmentRepo)
# Please add your team members' names here. 

## Team members' names 

1. Student Name:

   Student UT EID:

2. Student Name:

   Student UT EID:

 ...

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515
    


# Add your Project REPORT HERE 


# Project Template

This is a Java Maven Project Template


# How to compile the project

We use Apache Maven to compile and run this project. 

You need to install Apache Maven (https://maven.apache.org/)  on your system. 

Type on the command line: 

```bash
mvn clean compile
```

# How to create a binary runnable package 


```bash
mvn clean compile assembly:single
```


# How to run

```bash
mvn clean compile  exec:java -Dexec.args="WikipediaPagesOneDocPerLine.txt.bz2 300"
```



```bash
mvn clean compile  exec:java -Dexec.executable="edu.utexas.cs.cs378.Main"  -Dexec.args="WikipediaPagesOneDocPerLine.txt.bz2 500"
```


Input file is: 

WikipediaPagesOneDocPerLine.txt.bz2 

Data line batch size is: 500

You can modify the batch size based on available memory.


If you run this over SSH and it takes a lots of time you can run it in background using the following command

```bash
nohub mvn clean compile  exec:java -Dexec.executable="edu.utexas.cs.cs378.Main"  -Dexec.args="WikipediaPagesOneDocPerLine.txt.bz2 500"  & 
```

We recommend the above command for running the Main Java executable. 







