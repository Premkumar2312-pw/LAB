def depth_limited_crawl(graph, start, limit, visited=None, depth=0):
    if visited is None:
        visited = set()
    visited.add(start)
    print("  " * depth + start)
    if depth >= limit:
        return
    for neighbor in graph.get(start, []):
        if neighbor not in visited:
            depth_limited_crawl(graph, neighbor, limit, visited, depth + 1)

n = int(input("Enter number of web pages: "))
e = int(input("Enter number of links: "))

graph = {}
print("Enter links (from_url to_url):")
for _ in range(e):
    u, v = input().split()
    if u in graph:
        graph[u].append(v)
    else:
        graph[u] = [v]

start_page = input("Enter start page (e.g., www.tce.com): ")
depth_limit = int(input("Enter depth limit: "))

print("\nWeb crawling with Depth-Limited Search:")
depth_limited_crawl(graph, start_page, depth_limit)