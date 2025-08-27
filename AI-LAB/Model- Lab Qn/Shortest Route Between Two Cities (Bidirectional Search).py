from collections import deque

def bidirectional_search(graph, start, goal):
    if start == goal:
        return [start]

   
    frontier_start = deque([start])
    frontier_goal = deque([goal])

    visited_start = {start: None}  # city: parent
    visited_goal = {goal: None}

    while frontier_start and frontier_goal:
        current_start = frontier_start.popleft()
        for neighbor in graph[current_start]:
            if neighbor not in visited_start:
                visited_start[neighbor] = current_start
                frontier_start.append(neighbor)
            if neighbor in visited_goal:
                return reconstruct_path(visited_start, visited_goal, neighbor, start, goal)

        
        current_goal = frontier_goal.popleft()
        for neighbor in graph[current_goal]:
            if neighbor not in visited_goal:
                visited_goal[neighbor] = current_goal
                frontier_goal.append(neighbor)
            if neighbor in visited_start:
                return reconstruct_path(visited_start, visited_goal, neighbor, start, goal)

    return None

def reconstruct_path(visited_start, visited_goal, meeting_point, start, goal):
    path_start = []
    node = meeting_point
    while node:
        path_start.append(node)
        node = visited_start[node]
    path_start = path_start[::-1]  

    path_goal = []
    node = visited_goal[meeting_point]
    while node:
        path_goal.append(node)
        node = visited_goal[node]

    return path_start + path_goal

graph = {
    "Arad": ["Zerind", "Sibiu", "Timisoara"],
    "Zerind": ["Arad", "Oradea"],
    "Oradea": ["Zerind", "Sibiu"],
    "Sibiu": ["Arad", "Oradea", "Fagaras", "Rimnicu Vilcea"],
    "Fagaras": ["Sibiu", "Bucharest"],
    "Rimnicu Vilcea": ["Sibiu", "Pitesti", "Craiova"],
    "Pitesti": ["Rimnicu Vilcea", "Craiova", "Bucharest"],
    "Craiova": ["Rimnicu Vilcea", "Pitesti", "Dobreta"],
    "Dobreta": ["Craiova", "Mehadia"],
    "Mehadia": ["Dobreta", "Lugoj"],
    "Lugoj": ["Mehadia", "Timisoara"],
    "Timisoara": ["Lugoj", "Arad"],
    "Bucharest": ["Fagaras", "Pitesti", "Giurgiu", "Urziceni"],
    "Giurgiu": ["Bucharest"],
    "Urziceni": ["Bucharest", "Hirsova", "Vaslui"],
    "Hirsova": ["Urziceni", "Eforie"],
    "Eforie": ["Hirsova"],
    "Vaslui": ["Urziceni", "Iasi"],
    "Iasi": ["Vaslui", "Neamt"],
    "Neamt": ["Iasi"]
}

start_city = input("Enter start city: ")
goal_city = input("Enter goal city: ")

path = bidirectional_search(graph, start_city, goal_city)
if path:
    print("Shortest path found using Bidirectional Search:")
    print(" -> ".join(path))
else:
    print("No path found between the cities.")