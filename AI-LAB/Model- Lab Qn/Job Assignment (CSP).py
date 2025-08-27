import sys

def backtrack(assignment, workers, jobs, cost_matrix, best_solution, best_cost):
    if len(assignment) == len(workers):
        total_cost = sum(cost_matrix[w][j] for w, j in enumerate(assignment))
        if total_cost < best_cost[0]:
            best_cost[0] = total_cost
            best_solution[0] = assignment[:]
        return
    
    worker = len(assignment)
    for job in range(len(jobs)):
        if job not in assignment:
            assignment.append(job)
            backtrack(assignment, workers, jobs, cost_matrix, best_solution, best_cost)
            assignment.pop()

# User input
n = int(input("Enter number of workers/jobs: "))
print("Enter cost matrix row by row (space-separated):")
cost_matrix = [list(map(int, input().split())) for _ in range(n)]

workers = list(range(n))
jobs = list(range(n))
best_solution = [None]
best_cost = [sys.maxsize]

backtrack([], workers, jobs, cost_matrix, best_solution, best_cost)

print("\nOptimal Assignments:")
for w, j in enumerate(best_solution[0]):
    print(f"Worker {w+1} -> Job {j+1} with cost {cost_matrix[w][j]}")
print(f"\nTotal Minimum Cost: {best_cost[0]}")