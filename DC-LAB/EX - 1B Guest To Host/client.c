#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 8080
#define HOST_IP "10.0.2.2"   // VirtualBox NAT Host IP

int main() {
    int sock;
    struct sockaddr_in server_addr;
    char buffer[1024];

    // Create socket
    sock = socket(AF_INET, SOCK_STREAM, 0);

    // Configure server address
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT);
    inet_pton(AF_INET, HOST_IP, &server_addr.sin_addr);

    // Connect to server
    if (connect(sock, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        perror("Connection failed");
        return 1;
    }

    printf("Connected to Windows Host Chat Server\n");

    while (1) {
        // Send message
        printf("You: ");
        fgets(buffer, sizeof(buffer), stdin);
        write(sock, buffer, strlen(buffer));

        // Receive reply
        memset(buffer, 0, sizeof(buffer));
        read(sock, buffer, sizeof(buffer));
        printf("Host: %s", buffer);
    }

    close(sock);
    return 0;
}
