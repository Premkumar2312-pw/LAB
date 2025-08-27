import heapq

def heuristic(a, b):
    return abs(a[0]-b[0]) + abs(a[1]-b[1])

def astar(maze, start, goal):
    rows, cols = len(maze), len(maze[0])
    open_set = []
    heapq.heappush(open_set, (0 + heuristic(start, goal), 0, start))
    came_from = {}
    g_score = {start: 0}

    while open_set:
        _, cost, current = heapq.heappop(open_set)
        if current == goal:
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.append(start)
            return path[::-1], cost
        for dx, dy in [(-1,0),(1,0),(0,-1),(0,1)]:
            neighbor = (current[0]+dx, current[1]+dy)
            if 0 <= neighbor[0] < rows and 0 <= neighbor[1] < cols:
                if maze[neighbor[0]][neighbor[1]] == 1:
                    continue
                tentative_g = g_score[current]+1
                if neighbor not in g_score or tentative_g < g_score[neighbor]:
                    g_score[neighbor] = tentative_g
                    f_score = tentative_g + heuristic(neighbor, goal)
                    heapq.heappush(open_set, (f_score, tentative_g, neighbor))
                    came_from[neighbor] = current
    return None, None

# -------- User Input --------
rows = int(input("Enter number of rows: "))
cols = int(input("Enter number of columns: "))

maze = []
print("Enter the maze row by row (0=free, 1=wall), separated by spaces:")
for _ in range(rows):
    maze.append(list(map(int, input().split())))

start_row = int(input("Enter start row: "))
start_col = int(input("Enter start column: "))
goal_row = int(input("Enter goal row: "))
goal_col = int(input("Enter goal column: "))

start = (start_row, start_col)
goal = (goal_row, goal_col)

path, cost = astar(maze, start, goal)

if path:
    print("\nOptimal Path:")
    print(" -> ".join(str(p) for p in path))
    print("Total Cost:", cost)
else:
    print("No path found.")
