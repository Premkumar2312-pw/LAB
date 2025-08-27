from collections import defaultdict

class Graph:
    def __init__(self, vertices):
        self.graph = defaultdict(list)
        self.vertices = vertices

    def add_edge(self, u, v):
        self.graph[u].append(v)

    def dfs_util(self, v, visited, stack, rec_stack):
        visited[v] = True
        rec_stack[v] = True
        for neighbor in self.graph[v]:
            if not visited[neighbor]:
                if not self.dfs_util(neighbor, visited, stack, rec_stack):
                    return False
            elif rec_stack[neighbor]:
                # Cycle detected
                return False
        rec_stack[v] = False
        stack.append(v)
        return True

    def topological_sort(self):
        visited = [False] * self.vertices
        rec_stack = [False] * self.vertices
        stack = []
        for i in range(self.vertices):
            if not visited[i]:
                if not self.dfs_util(i, visited, stack, rec_stack):
                    return "Cycle detected; topological sort not possible"
        stack.reverse()
        return stack

def main():
    n, e = map(int, input("Enter number of vertices and edges: ").split())
    g_type = int(input("Enter 1 for directed graph, 0 for undirected graph: "))
    if g_type == 0:
        print("Topological sort is only defined for DAGs (directed acyclic graphs).")
        return

    g = Graph(n)
    print("Enter edges (u v):")
    for _ in range(e):
        u, v = map(int, input().split())
        g.add_edge(u, v)

    result = g.topological_sort()
    print("Topological order:" if isinstance(result, list) else "Error:", result)

if __name__ == "__main__":
    main()
