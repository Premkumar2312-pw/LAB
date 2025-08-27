import itertools
import math

drones = {
    "Drone1": {"capacity": 2, "max_distance": 15},
    "Drone2": {"capacity": 3, "max_distance": 20}
}

packages = {
    "PackageA": {"location": (2, 3)},
    "PackageB": {"location": (5, 1)},
    "PackageC": {"location": (4, 4)},
    "PackageD": {"location": (1, 5)}
}

base = (0, 0)

def distance(p1, p2):
    return math.sqrt((p1[0]-p2[0])**2 + (p1[1]-p2[1])**2)

def total_distance(drone_assignments):
    dist = {d: 0 for d in drones}
    for d in drones:
        assigned = [p for p, drone in drone_assignments.items() if drone == d]
        locs = [packages[p]["location"] for p in assigned]
        if locs:
            dist[d] += distance(base, locs[0])
            for i in range(len(locs)-1):
                dist[d] += distance(locs[i], locs[i+1])
            dist[d] += distance(locs[-1], base)
    return dist

def is_valid_assignment(drone_assignments):
    for d in drones:
        count = sum(1 for p in drone_assignments.values() if p == d)
        if count > drones[d]["capacity"]:
            return False
    dist = total_distance(drone_assignments)
    for d in drones:
        if dist[d] > drones[d]["max_distance"]:
            return False
    return True

def drone_delivery_csp(packages, drones):
    drones_list = list(drones.keys())
    package_list = list(packages.keys())
    best_solution = None
    min_total_dist = float('inf')
    for assignment in itertools.product(drones_list, repeat=len(package_list)):
        drone_assignments = dict(zip(package_list, assignment))
        if is_valid_assignment(drone_assignments):
            dist = sum(total_distance(drone_assignments).values())
            if dist < min_total_dist:
                min_total_dist = dist
                best_solution = drone_assignments
    return best_solution

solution = drone_delivery_csp(packages, drones)

if solution:
    print("Drone Delivery Assignment:")
    for package, drone in solution.items():
        print(f"{package} -> {drone}")
    print("Total Distances per Drone:", total_distance(solution))
else:
    print("No valid assignment found.")
