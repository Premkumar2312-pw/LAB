#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#define PORT 8080
int main() {
    int server_fd, client_socket;
    struct sockaddr_in server_addr;
    char buffer[1024];

    // Create socket
    server_fd = socket(AF_INET, SOCK_STREAM, 0);

    // Configure server address
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(PORT);

    // Bind
    bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));

    // Listen
    listen(server_fd, 1);

    printf("Chat Server started on port %d\n", PORT);
    printf("Server IP: ");
    system("hostname -I | awk '{print $1}'");

    // Accept client
    client_socket = accept(server_fd, NULL, NULL);
    printf("Client connected\n");

    while (1) {
        // Receive message
        memset(buffer, 0, sizeof(buffer));
        read(client_socket, buffer, sizeof(buffer));
        printf("Client: %s", buffer);
      
        // Send reply
        printf("You: ");
        fgets(buffer, sizeof(buffer), stdin);
        write(client_socket, buffer, strlen(buffer));
    }

    close(client_socket);
    close(server_fd);

    return 0;
}
