package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  //ahrs
 AHRS ahrs = new AHRS(SPI.Port.kMXP);
 double a = ahrs.getYaw();
  //motor
  VictorSPX motor1 = new VictorSPX(2);
  VictorSPX motor2 = new VictorSPX(0);
  VictorSPX motor3 = new VictorSPX(3);
  VictorSPX motor4 = new VictorSPX(4);
  // v
  double v = a * 1.5;
  //ki
  double errsum = 0;
  int c = 0;
  double b = Math.abs(a);
  double err = b - 0;
  double n = 0.015;
  @Override
  public void robotInit() {
    motor3.setInverted(false);
    motor4.setInverted(false);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
   ahrs.zeroYaw();
  }

 
  @Override
  public void autonomousPeriodic() {
   a = ahrs.getYaw();
   b = Math.abs(a);
   err = b - 0;
   n = 0.015;
   
   c++;
   errsum += err;
   v = (err + errsum) * n;
   double i = (errsum /= c)*-1;
   v = (err + i)*n;
   SmartDashboard.putNumber("err", err);
   SmartDashboard.putNumber("errsum", errsum);
   SmartDashboard.putNumber("a", a);
   SmartDashboard.putNumber("v", v);

   if(a < 3 && a > -3){
    motor1.set(ControlMode.PercentOutput,0.0);
    motor2.set(ControlMode.PercentOutput,0.0);
    motor3.set(ControlMode.PercentOutput,0.0);
    motor4.set(ControlMode.PercentOutput,0.0);
   }
   else if(a < 180 && a > 0 ){
    motor1.set(ControlMode.PercentOutput,-v);
    motor2.set(ControlMode.PercentOutput,-v);
    motor3.set(ControlMode.PercentOutput,-v);
    motor4.set(ControlMode.PercentOutput,-v);
   }else if(a > -180 && a < 0 ){
    motor1.set(ControlMode.PercentOutput,v);
    motor2.set(ControlMode.PercentOutput,v);
    motor3.set(ControlMode.PercentOutput,v);
    motor4.set(ControlMode.PercentOutput,v);
   }
   
    }

  
  @Override
  public void teleopPeriodic() {
  }

  
  @Override
  public void testPeriodic() {
  }
}
