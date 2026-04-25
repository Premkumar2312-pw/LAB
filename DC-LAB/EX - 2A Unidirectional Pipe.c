#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>

#define BUFFER_SIZE 1024
int main() {
    int fd[2];
    pid_t pid;
    char buffer[BUFFER_SIZE];
    ssize_t bytesRead;

    // Create pipe
    if (pipe(fd) == -1) {
        perror("Pipe failed");
        exit(1);
    }
    // Create child process
    pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    }

    // 🔹 Parent Process (Sender)
    if (pid > 0) {
        close(fd[0]);  // Close read end

        int inputFile = open("input.txt", O_RDONLY);
        if (inputFile < 0) {
            perror("Error opening input file");
            exit(1);
        }
        // Read from file and write to pipe
        while ((bytesRead = read(inputFile, buffer, BUFFER_SIZE)) > 0) {
            write(fd[1], buffer, bytesRead);
        }
        close(inputFile);
        close(fd[1]);  // Close write end
        wait(NULL);
        printf("File sent successfully.\n");
    }
    // 🔹 Child Process (Receiver)
    else {
        close(fd[1]);  // Close write end
        int outputFile = open("output.txt",
                              O_WRONLY | O_CREAT | O_TRUNC,
                              0644);
        if (outputFile < 0) {
            perror("Error opening output file");
            exit(1);
        }
        // Read from pipe and write to file
        while ((bytesRead = read(fd[0], buffer, BUFFER_SIZE)) > 0) {
            write(outputFile, buffer, bytesRead);
        }

        close(outputFile);
        close(fd[0]);  // Close read end

        printf("File received successfully.\n");
    }
    return 0;
}
