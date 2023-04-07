package com.unlam.progconcurrente.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class ProcessHandler {


    private static final String PROCESS_NAME_B = "B";
    private static final String PROCESS_NAME_C = "C";
    private static final String PROCESS_NAME_D = "D";
    private static final String PROCESS_NAME_E = "E";
    private static final String PROCESS_NAME_F = "F";
    private static final String PROCESS_NAME_G = "G";


    public void main() throws InterruptedException {
        ProcessHandle currentProcess = ProcessHandle.current();
        log.info("Proceso A con PID " + currentProcess.pid() + " y PPID " + currentProcess.parent().get().pid());

        CompletableFuture<Void> bFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_B, currentProcess.pid()));
        CompletableFuture<Void> cFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_C, currentProcess.pid()));
        CompletableFuture<Void> dFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_D, currentProcess.pid()));

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(bFuture, cFuture, dFuture);
        combinedFuture.join();
        Thread.sleep(25000);
    }

    private static void crearProceso(String letra, long ppid) {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "out", "Main", letra);
        try {
            Process p = pb.start();
            p.waitFor();
            log.info("Proceso " + letra + " con PID " + p.pid() + " y PPID " + ppid);

            if (letra.equals(PROCESS_NAME_B)) {
                CompletableFuture<Void> eFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_E, p.pid()));
                CompletableFuture<Void> fFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_F, p.pid()));

                CompletableFuture<Void> combinedBFuture = CompletableFuture.allOf(eFuture, fFuture);
                combinedBFuture.join();
            } else if (letra.equals("D")) {
                CompletableFuture<Void> gFuture = CompletableFuture.runAsync(() -> crearProceso(PROCESS_NAME_G, p.pid()));
                gFuture.join();
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}