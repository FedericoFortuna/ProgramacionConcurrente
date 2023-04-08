#include <iostream>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

void crear_proceso(char letra) {
    pid_t pid = getpid();
    pid_t ppid = getppid();
    cout << "Proceso " << letra << " con PID " << pid << " y PPID " << ppid << endl;

    if (letra == 'A') {
        pid_t b, c, d;
        b = fork();
        if (b == 0) {
            crear_proceso('B');
            exit(0);
        }
        c = fork();
        if (c == 0) {
            crear_proceso('C');
            exit(0);
        }
        d = fork();
        if (d == 0) {
            crear_proceso('D');
            exit(0);
        }
        waitpid(b, NULL, 0);
        waitpid(c, NULL, 0);
        waitpid(d, NULL, 0);
    }
    else if (letra == 'B') {
        pid_t e, f;
        e = fork();
        if (e == 0) {
            crear_proceso('E');
            exit(0);
        }
        f = fork();
        if (f == 0) {
            crear_proceso('F');
            exit(0);
        }
        waitpid(e, NULL, 0);
        waitpid(f, NULL, 0);
    }
    else if (letra == 'D') {
        pid_t g;
        g = fork();
        if (g == 0) {
            crear_proceso('G');
            exit(0);
        }
        waitpid(g, NULL, 0);
    }
}

int main() {
    pid_t proceso_a;
    proceso_a = fork();
    if (proceso_a == 0) {
        crear_proceso('A');
        exit(0);
    }
    waitpid(proceso_a, NULL, 0);
    return 0;
}
