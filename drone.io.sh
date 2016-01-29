#!/bin/bash

echo adding elasticsearch repository
gpg --keyserver pgpkeys.mit.edu --recv-key C90F9CB90E1FAD0C && gpg --export --armor C90F9CB90E1FAD0C | sudo apt-key add - 2> /dev/null > /dev/null
wget -qO - https://packages.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add - > /dev/null
echo "deb http://packages.elastic.co/elasticsearch/1.7/debian stable main" | sudo tee -a /etc/apt/sources.list > /dev/null

echo delete multiple google chrome entries
sudo rm /etc/apt/sources.list.d/google*
sudo apt-get update > /dev/null

sudo apt-get update > /dev/null
sudo apt-get install mlocate -y 2> /dev/null > /dev/null

sudo updatedb
sudo rm -rf `locate elasticsearch | egrep -v "/etc/apt/sources.list.d|/home"`
sudo updatedb

sudo apt-get purge openjdk-6-jdk openjdk-6-jre openjdk-6-jre-headless openjdk-6-jre-lib -y > /dev/null
sudo apt-get purge openjdk-7-jdk openjdk-7-jre openjdk-7-jre-headless openjdk-7-jre-lib -y > /dev/null
sudo apt-get autoremove -y 2> /dev/null > /dev/null


echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true |  sudo debconf-set-selections

sudo apt-get install oracle-java8-installer -y 2> /dev/null > /dev/null
sudo apt-get update 2> /dev/null > /dev/null
sudo updatedb 2> /dev/null > /dev/null

sudo apt-get install elasticsearch -y

echo sudo apt-get autoremove > /dev/null 2> /dev/null

wget -q "http://apache.uberglobalmirror.com/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz"
tar zxf apache-maven-${MAVEN_VERSION}-bin.tar.gz

sudo mv apache-maven-${MAVEN_VERSION} /opt
rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

echo | sudo tee -a /etc/default/elasticsearch 
echo 'ES_JAVA_OPTS="-Djava.net.preferIPv4Stack=true"' | sudo tee -a /etc/default/elasticsearch 

sudo service elasticsearch restart
sleep 10


