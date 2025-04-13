package org.sudokusolver.B_useCases;

import java.util.List;

public class DownloadSudokuFromApiUseCase {
    private final UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort;

    public DownloadSudokuFromApiUseCase(UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort) {
        this.useCase2HttpGatewayOutputPort = useCase2HttpGatewayOutputPort;
    }


    public List<String> downloadJsonStrings(){
        return useCase2HttpGatewayOutputPort.getSudokuJsonStrings();
    }
}
