#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>

#define BUFFER_SIZE 1024
int main() {
    int pipe1[2];   // Parent -> Child
    int pipe2[2];   // Child -> Parent
    pid_t pid;
    char buffer[BUFFER_SIZE];
    ssize_t bytesRead;
    // Create two pipes
    if (pipe(pipe1) == -1 || pipe(pipe2) == -1) {
        perror("Pipe creation failed");
        exit(1);
    }
    // Fork process
    pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    }
    // 🔹 Parent Process
    if (pid > 0) {
        close(pipe1[0]);  // Close read end of pipe1
        close(pipe2[1]);  // Close write end of pipe2
        int inputFile = open("input.txt", O_RDONLY);
        if (inputFile < 0) {
            perror("Error opening input file");
            exit(1);
        }
        // Send file data to child
        while ((bytesRead = read(inputFile, buffer, BUFFER_SIZE)) > 0) {
            write(pipe1[1], buffer, bytesRead);
        }
        close(inputFile);
        close(pipe1[1]);  // Signal EOF to child
        printf("Parent: File sent to child.\n");
        // Receive acknowledgment from child
        memset(buffer, 0, BUFFER_SIZE);
        read(pipe2[0], buffer, BUFFER_SIZE);
        printf("Parent received message: %s\n", buffer);

        close(pipe2[0]);
        wait(NULL);
    }
    // 🔹 Child Process
    else {
        close(pipe1[1]);  // Close write end of pipe1
        close(pipe2[0]);  // Close read end of pipe2

        int outputFile = open("output.txt",
                              O_WRONLY | O_CREAT | O_TRUNC,
                              0644);
        if (outputFile < 0) {
            perror("Error opening output file");
            exit(1);
        }
        // Receive file data from parent
        while ((bytesRead = read(pipe1[0], buffer, BUFFER_SIZE)) > 0) {
            write(outputFile, buffer, bytesRead);
        }
        close(outputFile);
        close(pipe1[0]);
        printf("Child: File received and saved.\n");
        // Send acknowledgment back to parent
        char message[] = "File received successfully!";
        write(pipe2[1], message, strlen(message) + 1);
        close(pipe2[1]);
    }

    return 0;
}
