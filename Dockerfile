FROM openjdk:8

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update -y && \
  apt-get upgrade -y && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  apt-get install -y byobu curl git htop man unzip vim wget maven nano && \
  apt-get install -y net-tools iputils-ping && \
  rm -rf /var/lib/apt/lists/*

# Set environment variables.
ENV HOME /root

# Define working directory.
WORKDIR /root

# Installing Manager
RUN \
  git clone https://github.com/paulofelipefeitosa/shopify-intern-challenge.git

# Define working directory.
WORKDIR /root/shopify-intern-challenge

RUN \
  mvn install -Dmaven.test.skip=true

CMD java -jar target/developer-intern-challenge-0.0.1-SNAPSHOT.jar && tail -f /dev/null
