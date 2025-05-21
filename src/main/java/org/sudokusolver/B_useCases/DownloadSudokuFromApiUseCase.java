package org.sudokusolver.B_useCases;

public class DownloadSudokuFromApiUseCase {
    private final UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort;

    public DownloadSudokuFromApiUseCase(UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort) {
        this.useCase2HttpGatewayOutputPort = useCase2HttpGatewayOutputPort;
    }


    public String downloadJsonStrings(){
        return useCase2HttpGatewayOutputPort.getSudokuJsonString();
    }
}
