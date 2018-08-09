DOC v0.9/23 JULY 2018

# FundAlly

## Introduction
This project solves the problem related to resource request chain created by faculties from origin to it's completion.
It's a project that involves interfaces for the following users:

1. Admin (dir: adminFundAlly)
2. Faculty (dir: facultyFundAlly)
3. Finance (dir: financeFundAlly)
4. Procurement (dir: procurementFundAlly)
5. Chief Administrator (dir: chiefAdminFundAlly)
6. User Accounts (dir: accountsFundAlly)

Each of the above user has a specific role in the overall project. The role description and use cases can be found in the README.md of respective directory.

## Installation

### Software Required
1. Ubuntu 16.04
2. Tomcat server 9
3. Java 8+
4. MySQL 5.6
5. Eclipse IDE (Oxygen 64bit)
6. Spring* 5
7. Hibernate* 5.2

\* Already included in the project. No need to import.

### How to
   <!-- Installation of external softwares. -->
#### Install Java
Follow the tutorial given in the link below.

    https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-get-on-ubuntu-16-04

#### Install Tomcat
Follow the tutorial given in the link below.

    https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04

#### Install Eclipse
Follow the steps below to install Eclipse.
1. Download the recommended Eclipse version(i.e. Oxygen 64bit) from the official website.

        https://www.eclipse.org/downloads/download.php?file=/oomph/epp/oxygen/R2/eclipse-inst-linux64.tar.gz

2. Extract the tar file in a required directory(e.g. $HOME or /usr/local)
3. Go to the root directory and execute the eclipse application by clicking on it.


#### Import Eclipse project
1. Put the above six mentioned directories (e.g. adminFundAlly, facultyFundAlly etc.) to eclipse-workspace.
2. Select File->Import. The Import Dialog box will appear.
3. Go to General->Existing Projects into Workspace. Select Next to proceed.
4. Choose eclipse-workspace as root directory.
5. Complete the following by keeping defaults. Atlast select Finish.
6. The project will be imported to the Eclipse.


### Configuration
   <!-- It deals with project deployment. -->

## Getting started


## Credits
  * Chad Darby(Spring and Hibernate instructor on Udemy)

## Contact
  * For queries you can contact me on raviprakash.coder@gmail.com

## License
Apache License 2.0. For details please go through the LICENSE file in the parent directory or visit http://www.apache.org/licenses/.
