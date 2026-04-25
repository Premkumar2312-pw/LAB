#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#pragma comment(lib, "ws2_32.lib")
#define PORT 8080

int main() {
    WSADATA wsa;
    SOCKET server_fd, client_socket;
    struct sockaddr_in server_addr;
    char buffer[1024];

    // Initialize Winsock
    WSAStartup(MAKEWORD(2, 2), &wsa);
    // Create socket
    server_fd = socket(AF_INET, SOCK_STREAM, 0);

    // Configure address
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(PORT);

    // Bind
    bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));

    // Listen
    listen(server_fd, 1);

    printf("Windows Host Chat Server running on port %d\n", PORT);
    printf("Waiting for Linux Guest...\n");

    // Accept connection
    client_socket = accept(server_fd, NULL, NULL);
    printf("Guest connected\n");

    while (1) {
        // Receive message
        memset(buffer, 0, sizeof(buffer));
        recv(client_socket, buffer, sizeof(buffer), 0);
        printf("Guest: %s", buffer);

        // Send reply
        printf("You: ");
        fgets(buffer, sizeof(buffer), stdin);
        send(client_socket, buffer, strlen(buffer), 0);
    }

    // Close sockets
    closesocket(client_socket);
    closesocket(server_fd);
    WSACleanup();

    return 0;
}
