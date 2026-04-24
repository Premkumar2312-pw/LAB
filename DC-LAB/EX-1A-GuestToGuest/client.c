#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 8080

int main() {
    int sock;
    struct sockaddr_in server_addr;
    char buffer[1024];
    char server_ip[20];

    printf("Enter Server IP: ");
    scanf("%s", server_ip);
    getchar();

    // Create socket
    sock = socket(AF_INET, SOCK_STREAM, 0);

    // Configure server address
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT);
    inet_pton(AF_INET, server_ip, &server_addr.sin_addr);

    // Connect to server
    connect(sock, (struct sockaddr*)&server_addr, sizeof(server_addr));
    printf("Connected to Chat Server\n");

    while (1) {
        // Send message
        printf("You: ");
        fgets(buffer, sizeof(buffer), stdin);
        write(sock, buffer, strlen(buffer));

        // Receive reply
        memset(buffer, 0, sizeof(buffer));
        read(sock, buffer, sizeof(buffer));
        printf("Server: %s", buffer);
    }

    close(sock);

    return 0;
}
