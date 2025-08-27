import heapq

graph = {
    'arad': {'zerind': 75, 'sibiu': 140, 'timisoara': 118},
    'zerind': {'arad': 75, 'oradea': 71},
    'oradea': {'zerind': 71, 'sibiu': 151},
    'sibiu': {'arad': 140, 'oradea': 151, 'fagaras': 99, 'rimnicu vilcea': 80},
    'timisoara': {'arad': 118, 'lugoj': 111},
    'lugoj': {'timisoara': 111, 'mehadia': 70},
    'mehadia': {'lugoj': 70, 'dobreta': 75},
    'dobreta': {'mehadia': 75, 'craiova': 120},
    'craiova': {'dobreta': 120, 'rimnicu vilcea': 146, 'pitesti': 138},
    'rimnicu vilcea': {'sibiu': 80, 'craiova': 146, 'pitesti': 97},
    'fagaras': {'sibiu': 99, 'bucharest': 211},
    'pitesti': {'rimnicu vilcea': 97, 'craiova': 138, 'bucharest': 101},
    'bucharest': {'fagaras': 211, 'pitesti': 101, 'giurgiu': 90, 'urziceni': 85},
    'giurgiu': {'bucharest': 90},
    'urziceni': {'bucharest': 85, 'vaslui': 142, 'hirsova': 98},
    'hirsova': {'urziceni': 98, 'eforie': 86},
    'eforie': {'hirsova': 86},
    'vaslui': {'urziceni': 142, 'iasi': 92},
    'iasi': {'vaslui': 92, 'neamt': 87},
    'neamt': {'iasi': 87}
}

heuristic = {
    'arad': 366, 'zerind': 374, 'oradea': 380, 'sibiu': 253,
    'timisoara': 329, 'lugoj': 244, 'mehadia': 241, 'dobreta': 242,
    'craiova': 160, 'rimnicu vilcea': 193, 'fagaras': 178, 'pitesti': 98,
    'bucharest': 0, 'giurgiu': 77, 'urziceni': 80, 'hirsova': 151,
    'eforie': 161, 'vaslui': 199, 'iasi': 226, 'neamt': 234
}

def greedy_best_first_search(graph, heuristic, start, goal):
    frontier = []
    heapq.heappush(frontier, (heuristic[start], start, [start]))
    explored = set()
    while frontier:
        _, current_city, path = heapq.heappop(frontier)
        if current_city == goal:
            return path
        explored.add(current_city)
        for neighbor in graph.get(current_city, {}):
            if neighbor not in explored:
                heapq.heappush(frontier, (heuristic.get(neighbor, float('inf')), neighbor, path + [neighbor]))
    return None

def path_cost(graph, path):
    total = 0
    for i in range(len(path)-1):
        total += graph[path[i]][path[i+1]]
    return total

# user input
start_city = input("Enter start city: ").strip().lower()
goal_city = input("Enter goal city: ").strip().lower()

if start_city not in graph or goal_city not in graph:
    print("Invalid city name(s). Please check your input.")
else:
    path = greedy_best_first_search(graph, heuristic, start_city, goal_city)
    if path:
        cost = path_cost(graph, path)
        print("Path found:", '->'.join(city.capitalize() for city in path))
        print("Cost:", cost)
    else:
        print("No path found between the specified cities.")
