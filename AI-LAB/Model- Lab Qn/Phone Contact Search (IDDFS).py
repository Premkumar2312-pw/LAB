class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_end = False

def build_trie(contacts):
    root = TrieNode()
    for contact in contacts:
        node = root
        for ch in contact:
            if ch not in node.children:
                node.children[ch] = TrieNode()
            node = node.children[ch]
        node.is_end = True
    return root

def iddfs_search(root, target):
    def dls(node, depth, path):
        if depth == 0:
            if node.is_end and ''.join(path) == target:
                return True
            return False
        if not node.children:
            return False
        next_char = target[len(path)]
        if next_char in node.children:
            path.append(next_char)
            found = dls(node.children[next_char], depth-1, path)
            if found:
                return True
            path.pop()
        return False

    for depth in range(1, len(target)+1):
        path = []
        print(f"Step {depth}: Searching for prefix '{target[:depth]}'")
        if dls(root, depth, path):
            print(f"Contact '{target}' found at step {depth}!")
            return
    print(f"Contact '{target}' not found.")

n = int(input("Enter number of contacts: "))
contacts = [input("Enter contact name: ") for _ in range(n)]
target = input("Enter contact to search: ")

trie_root = build_trie(contacts)
iddfs_search(trie_root, target)