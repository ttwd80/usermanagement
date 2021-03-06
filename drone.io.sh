#!/bin/bash
echo delete multiple google chrome entries
sudo rm /etc/apt/sources.list.d/google*
sudo apt-get update > /dev/null
wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
echo "deb http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee -a /etc/apt/sources.list.d/google.list > /dev/null
sudo apt-get update > /dev/null
sudo apt-get purge google-chrome-unstable > /dev/null 2> /dev/null
sudo apt-get install google-chrome-stable -y > /dev/null 2> /dev/null
ls -l /usr/bin/google-chrome
sudo rm -f /etc/apt/sources.list.d/google.list

echo install mlocate
sudo apt-get update > /dev/null
sudo apt-get install mlocate -y 2> /dev/null > /dev/null
sudo updatedb
locate -b stdio.h | head -1

echo xvfb
sudo apt-get install xvfb -y 2> /dev/null > /dev/null
sudo sed -i "s/1024x768x24/1920x1024x24/g" /etc/init/xvfb.conf
sudo start xvfb
sudo ps -ef | grep -i xvfb | grep RANDR

echo adding elasticsearch repository
sudo rm -rf `locate elasticsearch | egrep -v "/etc/apt/sources.list.d|/home"`
gpg --keyserver pgpkeys.mit.edu --recv-key C90F9CB90E1FAD0C 2> /dev/null > /dev/null
gpg --export --armor C90F9CB90E1FAD0C | sudo apt-key add - 2> /dev/null > /dev/null
wget -qO - https://packages.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add - > /dev/null
echo "deb http://packages.elastic.co/elasticsearch/1.7/debian stable main" | sudo tee -a /etc/apt/sources.list.d/elasticsearch-1.7.list > /dev/null
sudo updatedb
sudo apt-get update 2> /dev/null > /dev/null
sudo apt-get install elasticsearch -y  2> /dev/null > /dev/null
echo | sudo tee -a /etc/default/elasticsearch 
echo 'ES_JAVA_OPTS="-Djava.net.preferIPv4Stack=true"' | sudo tee -a /etc/default/elasticsearch > /dev/null
sudo service elasticsearch start
sleep 10
sudo netstat -pnlt | grep "9.0."
sudo service elasticsearch status

echo purge openjdk
sudo apt-get purge openjdk-6-jdk openjdk-6-jre openjdk-6-jre-headless openjdk-6-jre-lib -y > /dev/null
sudo apt-get purge openjdk-7-jdk openjdk-7-jre openjdk-7-jre-headless openjdk-7-jre-lib -y > /dev/null
sudo apt-get autoremove -y 2> /dev/null > /dev/null

echo install oracle jdk
sudo apt-get install oracle-java8-installer -y 2> /dev/null > /dev/null
sudo apt-get update 2> /dev/null > /dev/null
sudo apt-get autoremove -y > /dev/null 2> /dev/null
sudo updatedb 2> /dev/null > /dev/null
ls -l `which java`

echo installing maven
wget -q "http://apache.uberglobalmirror.com/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz"
tar zxf apache-maven-${MAVEN_VERSION}-bin.tar.gz
sudo mv apache-maven-${MAVEN_VERSION} /opt
rm apache-maven-${MAVEN_VERSION}-bin.tar.gz
export PATH=/opt/apache-maven-${MAVEN_VERSION}/bin:${PATH}
mvn --version | head -1

echo handle chrome driver
cd /tmp
wget -q http://chromedriver.storage.googleapis.com/2.20/chromedriver_linux64.zip
unzip chromedriver_linux64.zip
sudo netstat -pnlt | grep 9515
./chromedriver &
sleep 10
sudo netstat -pnlt | grep 9515
cd - 


chmod a+x init.sh
./init.sh
