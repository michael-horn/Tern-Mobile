/*
 * @(#) Sensor.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tern.language;

import tern.compiler.*;
import topcodes.TopCode;


public class Sensor extends PStatement {
	
	public static final int PRESS   = 419;
	public static final int RELEASE = 425;
	public static final int LIGHT   = 453;
	public static final int DARK    = 465;
	
	
	public Sensor(TopCode top) {
		super(top);
	}
	
	
	public static void register() {
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(PRESS)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(RELEASE)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(LIGHT)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(DARK)));
	}
	
	
	public int getCode() {
		return top.getCode();
	}
	
	
	public String getName() {
		switch (top.getCode()) {
		case PRESS:
			return "UNTIL-PRESS";
		case RELEASE:
			return "UNTIL-RELEASE";
		case LIGHT:
			return "UNTIL-LIGHT";
		case DARK:
			return "UNTIL-DARK";
		default:
			return "SENSOR";
		}
	}
	
	
	public Statement newInstance(TopCode top) {
		return new Sensor(top);
	}
	
	
	public String getTest() {
		switch (top.getCode()) {
		case PRESS:
			return "!SensorValueBool(SENSOR_1)";
		case RELEASE:
			return "SensorValueBool(SENSOR_1)";
		case DARK:
			return "SensorValue(SENSOR_3) > 48";
		case LIGHT:
			return "SensorValue(SENSOR_3) < 42";
		default:
			return "1";
		}
	}


	public String getSensorID() {
		switch (top.getCode()) {
		case PRESS:
		case RELEASE:
			return "SENSOR_1";
		case DARK:
		case LIGHT:
			return "SENSOR_3";
		default:
			return "SENSOR_1";
		}
	}
			
	

	public String getType() {
		switch (top.getCode()) {
		case PRESS:
		case RELEASE:
			return "SENSOR_TOUCH";
		case LIGHT:
		case DARK:
			return "SENSOR_LIGHT";
		default:
			return "SENSOR_TOUCH";
		}
	}

	
	public void compile(Program program) throws CompileException {
		setDebugInfo(program);
		if (this.next != null) next.compile(program);
	}
	
	
	public void toXML(java.io.PrintWriter out) {
		switch (top.getCode()) {
		case PRESS:   out.println("   <until-push />"); break;
		case RELEASE: out.println("   <until-release />"); break;
		case LIGHT:   out.println("   <until-light />"); break;
		case DARK:    out.println("   <until-dark />"); break;
		default:
		}
	}
}
