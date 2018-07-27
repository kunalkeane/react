package com.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.Comment;
import com.HashTag;

public class CommentUtil {
	
	private static final String HASHTAG = "#";
	private static final String SPACE = " ";
	
	public static List<String> getHashTag2(String comment){
		List<String> list = new ArrayList<String>();
		if(comment!=null && comment.trim().length() > 0 && comment.contains(HASHTAG)){
			Pattern MY_PATTERN = Pattern.compile("\\#(.*?)\\s");
			Matcher m = MY_PATTERN.matcher(comment);
			while (m.find()) {
			    String s = m.group(1);
			    list.add(s);
			}
		}
		return list;
	}
	
	public static Comment getComment(int id, List<HashTag> hashTag, String text){
		Comment comment = new Comment();
		comment.setId(id);
		//comment.setHashTag(hashTag);
		comment.setText(text);
		return comment;
	}
	
	public static List<String> getHashTag(String comment){
		List<String> list = new ArrayList<String>();
		if(comment!=null && comment.trim().length() > 0 && comment.contains(HASHTAG)){
			StringTokenizer sToken = new StringTokenizer(comment.trim(), SPACE);
			while(sToken.hasMoreTokens()){
				String token = sToken.nextToken().trim();
				if(token.startsWith(HASHTAG)){
					list.add(token.replaceAll(HASHTAG, ""));
				}
			}
		}
		return list;
	}
	
	public static void main(String args[]){
		List<String> list = getHashTag2(" #a23 abcd #techfiesta #infoquesta");
		System.out.println(list);
	}
	
	public static String getUserIdFromRequest(final HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if(principal != null)	{
			String name = principal.getName();
			String DOMAIN_NAME = "CORP\\";
			return name.replace(DOMAIN_NAME, "");
		}
		throw new RuntimeException("Could not get Principal Object");
	}

}
