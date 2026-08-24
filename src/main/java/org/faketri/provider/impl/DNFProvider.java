package org.faketri.provider.impl;

import org.faketri.dto.Dependency;
import org.faketri.dto.os.EPackage;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.provider.Provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DNFProvider implements Provider {

    private static final Logger log = BaseLoggerFactory.getLogger(DNFProvider.class);

    @Override
    public int canCheck(String systemProvider) {
        return systemProvider.equals(EPackage.DNF.getName()) ? 100 : 0;
    }

    @Override
    public boolean existInSystem(Dependency dependency) {
        boolean answer = false;
        ProcessBuilder processBuilder;
        processBuilder = new ProcessBuilder(EPackage.DNF.getCommand(), "rq", "--whatprovides", "*" +dependency.getName()+"*");
        try (Process pr = processBuilder.start()){
            log.debug("Process start {}", pr);
            log.debug("Command {}", processBuilder.command());
            BufferedReader bf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while (true){
                line = bf.readLine();
                if (line == null) break;
                log.info(line);
                answer = true;
            }
        } catch (IOException exception){
            log.error(exception.getMessage());
        }
        return answer;
    }
}
