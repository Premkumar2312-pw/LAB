import heapq

def uniform_cost_search(graph, start, goal):
 
    queue = []
    heapq.heappush(queue, (0, start, [start]))
    visited = set()

    while queue:
        cost, current, path = heapq.heappop(queue)
        if current == goal:
            return path, cost
        if current in visited:
            continue
        visited.add(current)
        for neighbor in graph[current]:
            if neighbor not in visited:
                edge_cost = graph[current][neighbor]
                heapq.heappush(queue, (cost + edge_cost, neighbor, path + [neighbor]))
    return None, None


graph = {
    "Arad": {"Zerind":75, "Sibiu":140, "Timisoara":118},
    "Zerind": {"Arad":75, "Oradea":71},
    "Oradea": {"Zerind":71, "Sibiu":151},
    "Sibiu": {"Arad":140, "Oradea":151, "Fagaras":99, "Rimnicu Vilcea":80},
    "Fagaras": {"Sibiu":99, "Bucharest":211},
    "Rimnicu Vilcea": {"Sibiu":80, "Pitesti":97, "Craiova":146},
    "Pitesti": {"Rimnicu Vilcea":97, "Craiova":138, "Bucharest":101},
    "Craiova": {"Rimnicu Vilcea":146, "Pitesti":138, "Dobreta":120},
    "Dobreta": {"Craiova":120, "Mehadia":75},
    "Mehadia": {"Dobreta":75, "Lugoj":70},
    "Lugoj": {"Mehadia":70, "Timisoara":111},
    "Timisoara": {"Lugoj":111, "Arad":118},
    "Bucharest": {"Fagaras":211, "Pitesti":101, "Giurgiu":90, "Urziceni":85},
    "Giurgiu": {"Bucharest":90},
    "Urziceni": {"Bucharest":85, "Hirsova":98, "Vaslui":142},
    "Hirsova": {"Urziceni":98, "Eforie":86},
    "Eforie": {"Hirsova":86},
    "Vaslui": {"Urziceni":142, "Iasi":92},
    "Iasi": {"Vaslui":92, "Neamt":87},
    "Neamt": {"Iasi":87}
}

start_city = input("Enter start city: ").strip().title()
goal_city = input("Enter goal city: ").strip().title()

path, total_cost = uniform_cost_search(graph, start_city, goal_city)
if path:
    print("Cheapest path found (Uniform Cost Search):")
    print(" -> ".join(path))
    print(f"Cost: {total_cost}")
else:
    print("No path found between the cities.")
