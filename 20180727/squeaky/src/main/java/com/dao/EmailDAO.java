package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Comment;
import com.DBConnection;
import com.service.UserDataAccess;


@Component
public class EmailDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	@Autowired
	DAOUtil daoUtil;
	private final static String SPACE  = " ";
	
		public  String getCommentsInXHours(int hours) {
			long startTime = new Date().getTime() - hours*60*60*1000;   //convert in miliseconds
			
			try {
				String sql = "select * from comment where updatetime > ?  order by updatetime desc limit 20";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(startTime));
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				
				Map<Long, String> activites = new TreeMap<Long, String>();
				
				for(Comment comment : commentsList){
					StringBuilder commentBuilder = new StringBuilder();
					commentBuilder.append("<p><i><font color=blue>").append(comment.getUserName()).append("</font>").append(SPACE).append("wrote </i> - ").
					append(SPACE).append(comment.getText()).append("</p>");
					activites.put(comment.getTimestamp(), commentBuilder.toString());
				}
				activites.putAll(getLikesInXHours(hours));
				if(activites.size() == 0){
					return null;
				}
				
				StringBuilder activityBuilder = new StringBuilder(); 
				activityBuilder.append("<html><body><br/>");
				for(String activity : activites.values()){
					activityBuilder.append(activity);
				}	
				activityBuilder.append("</body></html>");
				return activityBuilder.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Map<Long, String> getLikesInXHours(int hours) {
			long startTime = new Date().getTime() - hours*60*60*1000;   //convert in miliseconds
			Map<Long, String> likes = new TreeMap<Long, String>();
			
			
			try {
				String sql = "select * from likes a, comment b where a.updatetime > ? and (a.comment_id=b.id) order by a.updatetime desc limit 20";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(startTime));
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					StringBuilder sBuilder = new StringBuilder();
					sBuilder.append("<p><font color=green><i>").append(ldapDataAccessObject.getUserNameByUserId(rs.getString("a.user"))).append("</i></font>").append(SPACE).append("likes").
					append(SPACE).append("<font color=brown><i>").append(ldapDataAccessObject.getUserNameByUserId(rs.getString("b.user"))).
					append("'s").append("</font></i>").append(SPACE).append("comment").append("</p>");
					
					if(rs.getTimestamp("a.updatetime") != null){
						likes.put(rs.getTimestamp("a.updatetime").getTime(), sBuilder.toString());
					}
				}
				con.close();
				return likes;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  String[] getEmails() {
			
			List<String> emails =new ArrayList<String>();
			try {
				String sql = "select distinct email from users";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					emails.add(rs.getString("email"));
				}
				con.close();
				
				if(emails.size() > 0){
					return emails.toArray(new String[emails.size()]);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return null;
		}
		
		public static void main(String args[]){
			String emails = "Patil, Priyanka <priyanka.a.patil@capgemini.com>; Rai, Ajeet <ajeet.rai@capgemini.com>; P, Anjali <anjali.p@capgemini.com>; Deshmukh, Mohit <mohit.deshmukh@capgemini.com>; Nagaria, Saloni <saloni.nagaria@capgemini.com>; Pandey, Rohit <rohit.c.pandey@capgemini.com>; Agrawal, Abhinav <abhinav.agrawal@capgemini.com>; Jandial, Ankit <ankit.jandial@capgemini.com>; Salunkhe, Arati <arati.salunkhe@capgemini.com>; Jindam, Bhavana <bhavana.jindam@capgemini.com>; Chauhan, Deepeet <deepeet.chauhan@capgemini.com>; Dash, Dipty <dipty.a.dash@capgemini.com>; Bhansali, Harsh <harsh.bhansali@capgemini.com>; Shah, Malav <malav.a.shah@capgemini.com>; Balija, Nagaraju <nagaraju.balija@capgemini.com>; Gudage, Namrata <namrata.gudage@capgemini.com>; Jareniya, Navneet <navneet.jareniya@capgemini.com>; Patil, Sachin <sachin.j.patil@capgemini.com>; 'Apte, Pooja' <pooja.apte@capgemini.com>; Salvi, Pranali <pranali.salvi@capgemini.com>; Maurya, Pratibha <pratibha.maurya@capgemini.com>; 'Sahu, Sanjay' <sanjay.c.sahu@capgemini.com>; Govindarajan, Siddharth Srinivas <siddharth-srinivas.govindarajan@capgemini.com>; Patil, Vijay <vijay.a.patil@capgemini.com>; Kshirsagar, Ajinkya <ajinkya.kshirsagar@capgemini.com>; Verma, Vipin <vipin.a.verma@capgemini.com>; Dangur, Shantsagar <shantsagar.dangur@capgemini.com>; ., Abhilasha <abhilasha.abhilasha@capgemini.com>; Jadhav, Akshata <akshata.a.jadhav@capgemini.com>; Belhekar, Aniket <aniket.belhekar@capgemini.com>; Talluri, Gurubrahmachary <gurubrahmachary.talluri@capgemini.com>; Negi, Hemant <hemant.negi@capgemini.com>; Kajrolkar, Jyotshana <jyotshana.kajrolkar@capgemini.com>; Chaudhari, Kuntal <kuntal.chaudhari@capgemini.com>; Julasana, Nirav <nirav.julasana@capgemini.com>; Kajrolkar, Parag <parag.kajrolkar@capgemini.com>; Mehta, Parth <parth.a.mehta@capgemini.com>; Chandra, Priyansh <priyansh.chandra@capgemini.com>; Bhalerao, Sonal <sonal.bhalerao@capgemini.com>; Bommareddy, Tirupathi Reddy <tirupathi-reddy.bommareddy@capgemini.com>; Prasade, Parag <parag.prasade@capgemini.com>; Faridi, Danish <danish.faridi@capgemini.com>; Gupta, Abhishek <abhishek.q.gupta@capgemini.com>; Junghare, Ajinkya <ajinkya.junghare@capgemini.com>; Sarode, Akshay <akshay.sarode@capgemini.com>; Bhutani, Aman <aman.bhutani@capgemini.com>; Kumari, Archana <archana.g.kumari@capgemini.com>; Das, Arpita <arpita.a.das@capgemini.com>; Deshmukh, Ashutosh <ashutosh.deshmukh@capgemini.com>; Sandeep, Basireddy <basireddy.sandeep@capgemini.com>; Mehar, Dinesh <dinesh.mehar@capgemini.com>; Sethi, Geet <geet.sethi@capgemini.com>; Fakir, Ijaj <ijaj.fakir@capgemini.com>; 'Hora, Jasneet' <jasneet.hora@capgemini.com>; Tlau, Lallawmkima <lallawmkima.tlau@capgemini.com>; Garg, Manali <manali.garg@capgemini.com>; Jain, Meghna <meghna.a.jain@capgemini.com>; Kripalani, Neeraj <neeraj.kripalani@capgemini.com>; Mallabadi, Nikhil <nikhil.mallabadi@capgemini.com>; Gole, Nirzaree <nirzaree.gole@capgemini.com>; Musunuru, Nishanth <nishanth.musunuru@capgemini.com>; Ghugare, Nitin <nitin.ghugre@capgemini.com>; Narkhede, Pallavi <pallavi.a.narkhede@capgemini.com>; Pathak, Paras <paras.pathak@capgemini.com>; 'Saxena, Prashant' <prashant.saxena@capgemini.com>; Garg, Prince <prince.garg@capgemini.com>; Dutta, Priyasmita <priyasmita.dutta@capgemini.com>; Jain, Rahul <rahul.f.jain@capgemini.com>; Anand, Rohit <rohit.anand@capgemini.com>; Barbind, Ruchita <ruchita.barbind@capgemini.com>; Mathure, Sailee <sailee.mathure@capgemini.com>; Kamtalwar, Sandesh <sandesh.kamtalwar@capgemini.com>; Ganta, Shiva Kumar <shiva-kumar.ganta@capgemini.com>; Saxena, Shubham <shubham.saxena@capgemini.com>; Kala, Srinivasu <srinivasu.kala@capgemini.com>; Khombare, Sukhada <sukhada.khombare@capgemini.com>; Navadiya, Sukrity <sukrity.navadiya@capgemini.com>; Raut, Sushil <sushil.a.raut@capgemini.com>; Shaikh, Tarannum <tarannum.shaikh@capgemini.com>; Puranik, Tushar <puranik.rajesh@capgemini.com>; Shinkar, Unnati <unnati.shinkar@capgemini.com>; Mane, Varsha <varsha.mane@capgemini.com>; Raorane, Vinita <vinita.raorane@capgemini.com>; Tajan, Kshirod Chandra <kshirod-chandra.tajan@capgemini.com>; 'Tiwari, Anubhav' <anubhav.tiwari@capgemini.com>; Babhulgaonkar, Ganesh <ganesh.babhulgaonkar@capgemini.com>; 'Kazi, Imran' <imran.kazi@capgemini.com>; Bhuta, Ruchi <ruchi.bhuta@capgemini.com>; Jain, Sourabh <sourabh.a.jain@capgemini.com>; Musale, Suvarna <suvarna.musale@capgemini.com>; M, Yoganandam <yoganandam.m@capgemini.com>; Puthran, Aishwarya <aishwarya.puthran@capgemini.com>; Jadhav, Ajit <ajit.b.jadhav@capgemini.com>; Shingane, Anil <anil.shingane@capgemini.com>; Chaudhary, Anubhav <anubhav.chaudhary@capgemini.com>; Goja, Gautam <gautam.goja@capgemini.com>; Khanal, Hemant <hemant.khanal@capgemini.com>; Shah, Kamlesh <kamlesh.a.shah@capgemini.com>; Ramachandran, Karunya <karunya.ramachandiran@capgemini.com>; Rafi, M <m.rafi@capgemini.com>; Shah, Moin <moin.shah@capgemini.com>; Muthanna, Nikil <nikil.a.muthanna@capgemini.com>; Shelke, Pallavi <pallavi.shelke@capgemini.com>; Tamhane, Rahul <rahul.tamhane@capgemini.com>; Parepalli, Saiteja <saiteja.parepalli@capgemini.com>; Pandey, Salonee <salonee.pandey@capgemini.com>; Kota, Shirish <shirish.kota@capgemini.com>; Gaikwad, Shweta <shweta.gaikwad@capgemini.com>; Ganorkar, Swapnil <swapnil.ganorkar@capgemini.com>; Solanke, Vaibhav <vaibhav.solanke@capgemini.com>; Gaikwad, Vaishnavi <vaishnavi.gaikwad@capgemini.com>; Patil, Amruta <amruta.c.patil@capgemini.com>; Koranga, Laxman <laxman.koranga@capgemini.com>; Srivastava, Prateek <prateek.a.srivastava@capgemini.com>; Vishwakarma, Rashmi <rashmi.vishwakarma@capgemini.com>; Jain, Richa <richa.f.jain@capgemini.com>; Dive, Sachin <sachin.dive@capgemini.com>; Vattikunta, Sai Krishna <sai-krishna.vattikunta@capgemini.com>; Mal, Saksham <saksham.mal@capgemini.com>; Vijayvargiya, Shristi <shristi.vijayvargiya@capgemini.com>; Shinde, Trupti <trupti.a.shinde@capgemini.com>; Khemariya, Varun <varun.khemariya@capgemini.com>; Talkhe, Vivek <vivek.talkhe@capgemini.com>; Malani, Abhishek <abhishek.malani@capgemini.com>; Kumawat, Sandeep <sandeep.kumawat@capgemini.com>; Kumar, Saurabh <saurabh.kumar@capgemini.com>; Kadam, Amol <amol.b.kadam@capgemini.com>; Banerjee, Ankita <ankita.c.banerjee@capgemini.com>; Rajapurkar, Aparajita <aparajita.rajapurkar@capgemini.com>; Gandhi, Dipen <dipen.gandhi@capgemini.com>; Jangade, Kamlesh <kamlesh.jangade@capgemini.com>; Khandale, Mangesh <mangesh.a.khandale@capgemini.com>; Varghese, Nitha <nitha.a.varghese@capgemini.com>; Singh, Nivedita <nivedita.a.singh@capgemini.com>; Kulkarni, Prajakta <prajakta.kulkarni@capgemini.com>; Salvi, Pratik <pratik.salvi@capgemini.com>; Dandale, Priyanka <priyanka.dandale@capgemini.com>; Pandey, Puja <puja.a.pandey@capgemini.com>; Dhar, Sanchari <sanchari.dhar@capgemini.com>; Dhumale, Shruti <shruti.dhumale@capgemini.com>; Khandelwal, Shubhanshu <shubhanshu.khandelwal@capgemini.com>; Ingle, Sunil <sunil.ingle@capgemini.com>; Rathore, Sushmita <sushmita.rathore@capgemini.com>; Adhil, Syed <syed.adhil@capgemini.com>; Sen, Tanmoy <tanmoy.sen@capgemini.com>; Krishnasamy, Venkatesh Prabhu <venkatesh-prabhu-k.krishnasamy-a@capgemini.com>; Kedia, Vinayak <vinayak.kedia@capgemini.com>; Samala, Vipul <vipul.samala@capgemini.com>; Sharma, Ankur <ankur.e.sharma@capgemini.com>; Radhakrishnan, Gopinath <gopinath.radhakrishnan@capgemini.com>; Wadke, Harshal <harshal.wadke@capgemini.com>; Mehta, Jaydip <jaydip.mehta@capgemini.com>; Prabhakaran, Nitin <nitin.prabhakaran@capgemini.com>; Kalashetti, Sachin <sachin.kalashetti@capgemini.com>; Kotkar, Yogesh <yogesh.kotkar@capgemini.com>; Singh, Chandrakant <chandrakant.singh@capgemini.com>; Khan, Anis <anis.a.khan@capgemini.com>; Saxena, Aakash <aakash.saxena@capgemini.com>; Hoshing, Amol <amol.hoshing@capgemini.com>; Chilkoti, Deepti <deepti.chilkoti@capgemini.com>; Mitruka, Gagan <gagan.mitruka@capgemini.com>; Hundal, Gurminder <gurminder.hundal@capgemini.com>; Bali, Neha <neha.a.bali@capgemini.com>; Kumar, Prakash <prakash.k.kumar@capgemini.com>; Mukherjee, Priyanka <priyanka.mukherjee@capgemini.com>; Zagade, Rahul <rahul.zagade@capgemini.com>; Patil, Sachin <sachin.j.patil@capgemini.com>; Rembhotkar, Sakshi <sakshi.rembhotkar@capgemini.com>; Kalantri, Ashwin <ashwin.kalantri@capgemini.com>; Shinde, Vaibhav <vaibhav.shinde@capgemini.com>; Dhanapal, Akshara <akshara.dhanapal@capgemini.com>; Patil, Akshay <akshay.c.patil@capgemini.com>; Mathur, Amit <amit.mathur@capgemini.com>; Mujumale, Amol <amol.mujumale@capgemini.com>; Dhavale, Aniket <aniket.a.dhavale@capgemini.com>; Kumar, Gaurav <gaurav.l.kumar@capgemini.com>; Kiran, Gummalla <gummalla.kiran@capgemini.com>; Ganguly, Jyotirmoy <jyotirmoy.ganguly@capgemini.com>; Jha, Mantosh <mantosh.jha@capgemini.com>; Pokala, Naveen Kumar <naveen-kumar.pokala@capgemini.com>; Singh, Nidhi <nidhi.e.singh@capgemini.com>; Agrawal, Nikunj <nikunj.agrawal@capgemini.com>; Gole, Nilam <nilam.jawal@capgemini.com>; Velagala, Phani <phani.velagala@capgemini.com>; Tikare, Praneeta <praneeta.tikare@capgemini.com>; Yalasangi, Priyanka <priyanka.yalasangi@capgemini.com>; Shaik, Reyaz <reyaz.shaik@capgemini.com>; Midde, Sai Krishna <sai-krishna.midde@capgemini.com>; Poozhikala, Sajith <sajith.poozhikala@capgemini.com>; Kapoor, Shikhar <shikhar.a.kapoor@capgemini.com>; Malekar, Sirisha <sirisha.malekar@capgemini.com>; 'Zagade, Suraj' <suraj.zagade@capgemini.com>; 'Sahoo, Bhabani' <bhabani.a.sahoo@capgemini.com>; Gudi, Deekshitha Reddy <deekshitha-reddy.gudi@capgemini.com>; Kallagunta, Manojkumar <manojkumar.kallagunta@capgemini.com>; Khedekar, Shweta <shweta.khedekar@capgemini.com>; Manu, Sirish <sirish.manu@capgemini.com>; Gandhi, Ashish <ashish.a.gandhi@capgemini.com>; Singh, Abhishek <abhishek.ag.singh@capgemini.com>; Bhunya, Anita <anita.bhunya@capgemini.com>; Pitchi Reddy, Koppi Reddy <koppi-reddy.pitchi-reddy@capgemini.com>; Kumar, Manish <manish.ai.kumar@capgemini.com>; Ladia, Mohit <mohit.ladia@capgemini.com>; Chindarkar, Pranali <pranali.chindarkar@capgemini.com>; Kumari, Puja <puja.c.kumari@capgemini.com>; Jain, Roshan <roshan.a.jain@capgemini.com>; Rohini, Sagi <sagi.rohini@capgemini.com>; Panigrahi, Satyajeet <satyajeet.panigrahi@capgemini.com>; Sharma, Shilpa <shilpa.a.sharma@capgemini.com>; Gupta, Tanya <tanya.d.gupta@capgemini.com>; Prathibha, Varri <varri.prathibha@capgemini.com>; Khatri, Ajay <ajay.khatri@capgemini.com>; Mishra, Ajay <ajay.mishra@capgemini.com>; Deshmukh, Ajit <ajit.deshmukh@capgemini.com>; Satam, Akshay <akshay.a.satam@capgemini.com>; Nakate, Amit <amit.nakate@capgemini.com>; Chakraborty, Anindita <anindita.chakraborty@capgemini.com>; 'Gupta, Ankit' <ankit.j.gupta@capgemini.com>; Praveen, Anuraag <anuraag.praveen@capgemini.com>; Kopre, Ashish <ashish.kopre@capgemini.com>; Kallam, Basavaiah <basavaiah.kallam@capgemini.com>; 'Gupta, Gaurav' <gaurav.m.gupta@capgemini.com>; Kumar, Indra Bushan <indra-bushan.kumar@capgemini.com>; Sharma, Naveen <naveen.a.sharma@capgemini.com>; Pansare, Nehali <nehali.pansare@capgemini.com>; Kumar, Nikhil <nikhil.e.kumar@capgemini.com>; Pawar, Pankaj <pankaj.pawar@capgemini.com>; Kasbe, Prathamesh <prathamesh.kasbe@capgemini.com>; 'Sharma, Prerna' <prerna.a.sharma@capgemini.com>; Sovenahalli Karanam, Priyanka <priyanka.sovenahalli-karanam@capgemini.com>; Raghava, Rangeya <rangeya.raghava@capgemini.com>; M, Ravikanth <ravikanth.m@capgemini.com>; Chimirala Rama Mohan Gari, Roopesh <roopesh.chimirala-rama-mohan-gari@capgemini.com>; Yerramsetti, Sai Lakshmi <sai.yerramsetti@capgemini.com>; Sukale, Sarika <sarika.sukale@capgemini.com>; Rohilla, Shivam <shivam.rohilla@capgemini.com>; Addani, Srikanth <srikanth.addani@capgemini.com>; Deshpande, Sumukh <sumukh.deshpande@capgemini.com>; T, Vidyashree <vidyashree.t@capgemini.com>; Yadav, Vijay <vijay.d.yadav@capgemini.com>; Singh, Vishal <vishal.l.singh@capgemini.com>";
			int start = emails.indexOf("<");
			int end = emails.indexOf(">");
			while(start != -1){
				System.out.println(emails.substring(start+1, end));
				emails = emails.replaceFirst("<", "-");
				emails = emails.replaceFirst(">", "-");
				start = emails.indexOf("<");
				end = emails.indexOf(">");
			}
			
			
		}
		
		
}	

