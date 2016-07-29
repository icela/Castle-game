#include <stdlib.h>

int main(int argc, char* argv[]) {
	system("@echo off");
	system("echo Loading custom Java Runtime Environment...");
	system("jre\\bin\\java -jar cui.jar");
	return 0;
}
