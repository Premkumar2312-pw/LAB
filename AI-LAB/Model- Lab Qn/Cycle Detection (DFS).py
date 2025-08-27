def dfs_cycle(graph, node, visited, rec_stack, parent, path, cycles, directed):
    visited[node] = True
    path.append(node)
    for neighbor in graph[node]:
        if not visited[neighbor]:
            if dfs_cycle(graph, neighbor, visited, rec_stack, node, path, cycles, directed):
                return True
        elif (directed and rec_stack[neighbor]) or (not directed and neighbor != parent):
            cycles.append((node, neighbor))
            return True
    path.pop()
    rec_stack[node] = False
    return False

n = int(input("Enter number of vertices: "))
e = int(input("Enter number of edges: "))
graph_type = input("Directed or Undirected? (D/U): ").strip().upper()
directed = graph_type == 'D'

graph = [[] for _ in range(n)]
print("Enter edges (u v) with 0-based indexing:")
for _ in range(e):
    u, v = map(int, input().split())
    graph[u].append(v)
    if not directed:
        graph[v].append(u)

visited = [False] * n
rec_stack = [False] * n
cycles = []
has_cycle = False

for node in range(n):
    if not visited[node]:
        if dfs_cycle(graph, node, visited, rec_stack, -1, [], cycles, directed):
            has_cycle = True
            break

if has_cycle:
    print("Cycle detected in the graph")
    print("Edges causing the cycle:")
    for u, v in cycles:
        print(f"{u} -> {v}")
else:
    print("No cycle in the graph")