import multiprocessing
import os
import time

def crear_proceso(letra):
    pid = os.getpid()
    ppid = os.getppid()
    print(f"Proceso {letra} con PID {pid} y PPID {ppid}")

    if letra == "A":
        b = multiprocessing.Process(target=crear_proceso, args=("B",))
        c = multiprocessing.Process(target=crear_proceso, args=("C",))
        d = multiprocessing.Process(target=crear_proceso, args=("D",))
        b.start()
        c.start()
        d.start()
        b.join()
        c.join()
        d.join()
    elif letra == "B":
        e = multiprocessing.Process(target=crear_proceso, args=("E",))
        f = multiprocessing.Process(target=crear_proceso, args=("F",))
        e.start()
        f.start()
        e.join()
        f.join()
    elif letra == "D":
        g = multiprocessing.Process(target=crear_proceso, args=("G",))
        g.start()
        g.join()

if __name__ == '__main__':
    proceso_a = multiprocessing.Process(target=crear_proceso, args=("A",))
    proceso_a.start()
    proceso_a.join()
    time.sleep(15)
