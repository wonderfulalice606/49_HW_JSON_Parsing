#!/bin/sh

#===========================================================================================
GITHUB=wonderfulalice606
WS_DIR=workspace
DOC=Documents
REPO=49_HW_JSON_Parsing
RUNNER=Test_Runner

#===========================================================================================

if ! which java > /dev/null 2>&1; then echo Java is not installed; return; fi
if ! which mvn > /dev/null 2>&1; then echo Maven is not installed; return; fi
if ! which git > /dev/null 2>&1; then echo Git is not installed; return; fi

if [ -d "$HOME/$DOC/$WS_DIR/" ]; then cd ~/$DOC/$WS_DIR/$RUNNER; else echo $WS_DIR is not exist; return; fi
if [ -d "$HOME/$DOC/$WS_DIR/$RUNNER/$REPO" ]; then rm -rf $HOME/$DOC/$WS_DIR/$RUNNER/$REPO; fi

git clone https://github.com/$GITHUB/$REPO.git
cd ./$REPO

mvn package -Dbuild.version="1.1" -Dmain.Class="core.Main"

java -jar ./target/49_HW_JSON_Parsing-1.0-jar-with-dependencies.jar

mvn package -Dbuild.version="1.2" -Dmain.Class="core.JSON_Generate"

java -jar ./target/49_HW_JSON_Parsing-1.0-jar-with-dependencies.jar


echo "$HOME/$DOC/$WS_DIR/$RUNNER/$REPO"
