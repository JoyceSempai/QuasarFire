package com.cruiser.quasarfire.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;

import com.cruiser.quasarfire.dto.Position;
import com.cruiser.quasarfire.dto.Satellite;
import com.cruiser.quasarfire.exception.QuasarFireException;

@Service
public class TrilaterationService {
	
	private static final String SATELLITE_NAMES[] = {"kenobi","skywalker","sato"};
	private static final double POSITIONS[][] = {{-500, -200}, {100, -100}, {500, 100}};

	public Position getLocation(double distances[]) {
		if(distances.length != SATELLITE_NAMES.length) {
			throw new QuasarFireException();
		}
		
        double x1 = POSITIONS[1][0] - POSITIONS[0][0];
        double y1 = POSITIONS[1][1] - POSITIONS[0][1];
        double x2 = POSITIONS[2][0] - POSITIONS[0][0];
        double y2 = POSITIONS[2][1] - POSITIONS[0][1];
        
        double p1squared = Math.pow(x1 , 2) +  Math.pow(y1 ,2);
        double p2squared = Math.pow(x2 , 2) +  Math.pow(y2 ,2);


        double r0squared = distances[0] * distances[0];
        double r1squared = distances[1] * distances[1];
        double r2squared = distances[2] * distances[2];
        
        double[] d = new double[2];
        d[0] =(r0squared - r1squared + p1squared)/2.0;
        d[1] =(r0squared - r2squared + p2squared)/2.0;
        

        double[][] matrix = {{x1,y1}, {x2,y2}};
        RealMatrix a = new Array2DRowRealMatrix(matrix, false);
        RealVector b = new ArrayRealVector(d, false);
        DecompositionSolver solver = new QRDecomposition(a).getSolver();

        RealVector x;
        x = solver.solve(b);
        x = x.add(new ArrayRealVector(POSITIONS[0]));
        
        double[] position = x.toArray();
        
        Position positionResponse = new Position();
        positionResponse.setX(position[0]);
        positionResponse.setY(position[1]);
        
        return positionResponse;
	}
	
	public String getMessage(String[][] messages) { 
        
		if(messages == null || messages.length == 0) {
			throw new QuasarFireException();
		}
		
		int size = messages[0].length;
		
		for(String[] message: messages) {
			if (message.length != size) {
				throw new QuasarFireException();
			}
		}
		
		String[] message = new String[size];
		
		message = messages[0];
		for(int i = 1; i < messages.length; i++) {
			for(int j = 0; j < size; j++) {
				
				if(!"".equals(messages[i][j]) && "".equals(message[j])) {
					message[j] = messages[i][j];
				}
				else if(!"".equals(messages[i][j]) && (!"".equals(message[j])) && !messages[i][j].equals(message[j])) {
					throw new QuasarFireException();
				}
			}
		}
        
		StringBuilder newMessage = new StringBuilder();
		for(int i=0; i< message.length; i++) {
			newMessage.append(message[i]).append(" ");
		}
		
        return newMessage.toString().trim();
	}
	
	public List<Satellite> sortAndValidateSatellites(List<Satellite> satellites) {
		List<Satellite> newSatellites = new ArrayList<>();
		
		for(String satellite_name: SATELLITE_NAMES) {
			for(int i=0; i < satellites.size(); i++) {
				if(satellites.get(i).getName()!=null && satellites.get(i).getName().equals(satellite_name)) {
					newSatellites.add(satellites.get(i));
					satellites.remove(i);
					break;
				}
			}
		}
		
		if((newSatellites.size() != SATELLITE_NAMES.length) 
				|| satellites.size()>0) {
			throw new QuasarFireException();
		}
		
		return newSatellites;
	}
}
