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

    sock = socket(AF_INET, SOCK_STREAM, 0);

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT);
    inet_pton(AF_INET, server_ip, &server_addr.sin_addr);

    connect(sock, (struct sockaddr*)&server_addr, sizeof(server_addr));
    printf("Connected to server\n");

    while (1) {

        // SEND
        printf("You: ");
        fgets(buffer, sizeof(buffer), stdin);

        if (strncmp(buffer, "exit", 4) == 0) {
            write(sock, buffer, strlen(buffer));
            break;
        }

        write(sock, buffer, strlen(buffer));

        // RECEIVE
        memset(buffer, 0, sizeof(buffer));
        read(sock, buffer, sizeof(buffer));

        if (strncmp(buffer, "exit", 4) == 0) {
            printf("Server disconnected\n");
            break;
        }

        printf("Server: %s", buffer);
    }

    close(sock);
    return 0;
}
