import heapq

def beam_search(start, word_probs, beam_width=2, max_steps=5):
    heap = [(1.0, [start])]  
    for _ in range(max_steps):
        new_heap = []
        for prob, seq in heap:
            last_word = seq[-1]
            if last_word not in word_probs:
                new_heap.append((prob, seq))
                continue
            for next_word, p in word_probs[last_word].items():
                new_seq = seq + [next_word]
                new_prob = prob * p
                new_heap.append((new_prob, new_seq))
        heap = heapq.nlargest(beam_width, new_heap, key=lambda x: x[0])
    return heap[0] 
  
word_probs = {}
print("Enter word probabilities in the format: word next_word probability")
print("Type 'done' when finished:")
while True:
    line = input()
    if line.lower() == "done":
        break
    parts = line.split()
    if len(parts) != 3:
        print("Invalid input, try again.")
        continue
    word, next_word, prob = parts[0], parts[1], float(parts[2])
    if word not in word_probs:
        word_probs[word] = {}
    word_probs[word][next_word] = prob

start_word = input("Enter start word: ")
beam_width = int(input("Enter beam width: "))
max_steps = int(input("Enter max steps: "))

prob, completed_sentence = beam_search(start_word, word_probs, beam_width, max_steps)
print("Completed Sentence:", " ".join(completed_sentence))
print("Probability of sentence:", prob)
