///***********************************************************************
// * mt4j Copyright (c) 2008 - 2010 C.Ruff, Fraunhofer-Gesellschaft All rights reserved.
// *  
// *   This program is free software: you can redistribute it and/or modify
// *   it under the terms of the GNU General Public License as published by
// *   the Free Software Foundation, either version 3 of the License, or
// *   (at your option) any later version.
// *
// *   This program is distributed in the hope that it will be useful,
// *   but WITHOUT ANY WARRANTY; without even the implied warranty of
// *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *   GNU General Public License for more details.
// *
// *   You should have received a copy of the GNU General Public License
// *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *
// ***********************************************************************/
//package org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
//import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
//import java.awt.geom.Point2D;
//
//
//
///**
// * The Class MTDollarTemplates.
// */
//public class UnistrokeTemplates {
//	
//	/** The List of registered templates. */
//	List <Template> templates;
//
//	/** The Utilities. */
//	UnistrokeUtils du;
//	
//	/**
//	 * Instantiates new MTDollarTemplates.
//	 * 
//	 * @param templates the List of registered templates
//	 * @param du the Utils
//	 */
//	public UnistrokeTemplates (List<Template> templates, UnistrokeUtils du) {
//		this.templates = templates;
//		this.du = du;
//	}
//	
//	
//	
//	
//	/**
//	 * The Class Template.
//	 */
//	class Template{
//	  
//  	/** The gesture. */
//  	UnistrokeGesture gesture;
//	  
//  	/** The direction. */
//  	Direction direction;
//	  
//  	/** The Points. */
//  	List<Point2D.Float> Points;
//	  
//  	/**
//  	 * Instantiates a new template.
//  	 * 
//  	 * @param gesture the gesture
//  	 * @param points the points
//  	 * @param direction the direction
//  	 */
//  	Template(UnistrokeGesture gesture, List<Point2D.Float> points, Direction direction){
//	   this.gesture = gesture;
//	   this.direction = direction;
//	    Points = du.Resample( points, du.getNumPoints(), direction);
//	    Points = du.RotateToZero( Points );
//	    Points = du.ScaleToSquare( Points, du.getSquareSize());
//	    Points = du.TranslateToOrigin( Points );
//
//	  }
//	}
//
//	/**
//	 * Adds the template by gesture/direction.
//	 *
//	 * @param gesture the gesture
//	 * @param direction the direction
//	 */
//	public void addTemplate(UnistrokeGesture gesture, Direction direction) {
//		List<Point2D.Float> points = null;
//		switch (gesture) {
//		case TRIANGLE:
//			Point2D.Float[] point0_array = {
//			 new Point2D.Float(137,139),new Point2D.Float(135,141),new Point2D.Float(133,144),new Point2D.Float(132,146),
//             new Point2D.Float(130,149),new Point2D.Float(128,151),new Point2D.Float(126,155),new Point2D.Float(123,160),
//             new Point2D.Float(120,166),new Point2D.Float(116,171),new Point2D.Float(112,177),new Point2D.Float(107,183),
//             new Point2D.Float(102,188),new Point2D.Float(100,191),new Point2D.Float(95,195),new Point2D.Float(90,199),
//             new Point2D.Float(86,203),new Point2D.Float(82,206),new Point2D.Float(80,209),new Point2D.Float(75,213),
//             new Point2D.Float(73,213),new Point2D.Float(70,216),new Point2D.Float(67,219),new Point2D.Float(64,221),
//             new Point2D.Float(61,223),new Point2D.Float(60,225),new Point2D.Float(62,226),new Point2D.Float(65,225),
//             new Point2D.Float(67,226),new Point2D.Float(74,226),new Point2D.Float(77,227),new Point2D.Float(85,229),
//             new Point2D.Float(91,230),new Point2D.Float(99,231),new Point2D.Float(108,232),new Point2D.Float(116,233),
//             new Point2D.Float(125,233),new Point2D.Float(134,234),new Point2D.Float(145,233),new Point2D.Float(153,232),
//             new Point2D.Float(160,233),new Point2D.Float(170,234),new Point2D.Float(177,235),new Point2D.Float(179,236),
//             new Point2D.Float(186,237),new Point2D.Float(193,238),new Point2D.Float(198,239),new Point2D.Float(200,237),
//             new Point2D.Float(202,239),new Point2D.Float(204,238),new Point2D.Float(206,234),new Point2D.Float(205,230),
//             new Point2D.Float(202,222),new Point2D.Float(197,216),new Point2D.Float(192,207),new Point2D.Float(186,198),
//             new Point2D.Float(179,189),new Point2D.Float(174,183),new Point2D.Float(170,178),new Point2D.Float(164,171),
//             new Point2D.Float(161,168),new Point2D.Float(154,160),new Point2D.Float(148,155),new Point2D.Float(143,150),
//             new Point2D.Float(138,148),new Point2D.Float(136,148) };
//		points = Arrays.asList(point0_array);
//
//
//
//		break;
//
//
//		case X:
//			Point2D.Float [] point1_array = {
//				new Point2D.Float(87,142),new Point2D.Float(89,145),new Point2D.Float(91,148),new Point2D.Float(93,151),
//				new Point2D.Float(96,155),new Point2D.Float(98,157),new Point2D.Float(100,160),new Point2D.Float(102,162),
//				new Point2D.Float(106,167),new Point2D.Float(108,169),new Point2D.Float(110,171),new Point2D.Float(115,177),
//				new Point2D.Float(119,183),new Point2D.Float(123,189),new Point2D.Float(127,193),new Point2D.Float(129,196),
//				new Point2D.Float(133,200),new Point2D.Float(137,206),new Point2D.Float(140,209),new Point2D.Float(143,212),
//				new Point2D.Float(146,215),new Point2D.Float(151,220),new Point2D.Float(153,222),new Point2D.Float(155,223),
//             	new Point2D.Float(157,225),new Point2D.Float(158,223),new Point2D.Float(157,218),new Point2D.Float(155,211),
//             	new Point2D.Float(154,208),new Point2D.Float(152,200),new Point2D.Float(150,189),new Point2D.Float(148,179),
//             	new Point2D.Float(147,170),new Point2D.Float(147,158),new Point2D.Float(147,148),new Point2D.Float(147,141),
//             	new Point2D.Float(147,136),new Point2D.Float(144,135),new Point2D.Float(142,137),new Point2D.Float(140,139),
//             	new Point2D.Float(135,145),new Point2D.Float(131,152),new Point2D.Float(124,163),new Point2D.Float(116,177),
//             	new Point2D.Float(108,191),new Point2D.Float(100,206),new Point2D.Float(94,217),new Point2D.Float(91,222),
//             	new Point2D.Float(89,225),new Point2D.Float(87,226),new Point2D.Float(87,224) } ;
//			points = Arrays.asList(point1_array);
//
//			break;
//
//		case RECTANGLE:
//			Point2D.Float[] point2_array = {
//				 new Point2D.Float(78,149),new Point2D.Float(78,153),new Point2D.Float(78,157),new Point2D.Float(78,160),
//	             new Point2D.Float(79,162),new Point2D.Float(79,164),new Point2D.Float(79,167),new Point2D.Float(79,169),
//	             new Point2D.Float(79,173),new Point2D.Float(79,178),new Point2D.Float(79,183),new Point2D.Float(80,189),
//	             new Point2D.Float(80,193),new Point2D.Float(80,198),new Point2D.Float(80,202),new Point2D.Float(81,208),
//	             new Point2D.Float(81,210),new Point2D.Float(81,216),new Point2D.Float(82,222),new Point2D.Float(82,224),
//	             new Point2D.Float(82,227),new Point2D.Float(83,229),new Point2D.Float(83,231),new Point2D.Float(85,230),
//	             new Point2D.Float(88,232),new Point2D.Float(90,233),new Point2D.Float(92,232),new Point2D.Float(94,233),
//	             new Point2D.Float(99,232),new Point2D.Float(102,233),new Point2D.Float(106,233),new Point2D.Float(109,234),
//	             new Point2D.Float(117,235),new Point2D.Float(123,236),new Point2D.Float(126,236),new Point2D.Float(135,237),
//	             new Point2D.Float(142,238),new Point2D.Float(145,238),new Point2D.Float(152,238),new Point2D.Float(154,239),
//	             new Point2D.Float(165,238),new Point2D.Float(174,237),new Point2D.Float(179,236),new Point2D.Float(186,235),
//	             new Point2D.Float(191,235),new Point2D.Float(195,233),new Point2D.Float(197,233),new Point2D.Float(200,233),
//	             new Point2D.Float(201,235),new Point2D.Float(201,233),new Point2D.Float(199,231),new Point2D.Float(198,226),
//	             new Point2D.Float(198,220),new Point2D.Float(196,207),new Point2D.Float(195,195),new Point2D.Float(195,181),
//	             new Point2D.Float(195,173),new Point2D.Float(195,163),new Point2D.Float(194,155),new Point2D.Float(192,145),
//	             new Point2D.Float(192,143),new Point2D.Float(192,138),new Point2D.Float(191,135),new Point2D.Float(191,133),
//	             new Point2D.Float(191,130),new Point2D.Float(190,128),new Point2D.Float(188,129),new Point2D.Float(186,129),
//	             new Point2D.Float(181,132),new Point2D.Float(173,131),new Point2D.Float(162,131),new Point2D.Float(151,132),
//	             new Point2D.Float(149,132),new Point2D.Float(138,132),new Point2D.Float(136,132),new Point2D.Float(122,131),
//	             new Point2D.Float(120,131),new Point2D.Float(109,130),new Point2D.Float(107,130),new Point2D.Float(90,132),
//	             new Point2D.Float(81,133),new Point2D.Float(76,133)};
//			points = Arrays.asList(point2_array);
//
//			break;
//
//		case CIRCLE:
//			Point2D.Float[] point3_array = {
//				  new Point2D.Float(127,141),new Point2D.Float(124,140),new Point2D.Float(120,139),new Point2D.Float(118,139),
//	              new Point2D.Float(116,139),new Point2D.Float(111,140),new Point2D.Float(109,141),new Point2D.Float(104,144),
//	              new Point2D.Float(100,147),new Point2D.Float(96,152),new Point2D.Float(93,157),new Point2D.Float(90,163),
//	              new Point2D.Float(87,169),new Point2D.Float(85,175),new Point2D.Float(83,181),new Point2D.Float(82,190),
//	              new Point2D.Float(82,195),new Point2D.Float(83,200),new Point2D.Float(84,205),new Point2D.Float(88,213),
//	              new Point2D.Float(91,216),new Point2D.Float(96,219),new Point2D.Float(103,222),new Point2D.Float(108,224),
//	              new Point2D.Float(111,224),new Point2D.Float(120,224),new Point2D.Float(133,223),new Point2D.Float(142,222),
//	              new Point2D.Float(152,218),new Point2D.Float(160,214),new Point2D.Float(167,210),new Point2D.Float(173,204),
//	              new Point2D.Float(178,198),new Point2D.Float(179,196),new Point2D.Float(182,188),new Point2D.Float(182,177),
//	              new Point2D.Float(178,167),new Point2D.Float(170,150),new Point2D.Float(163,138),new Point2D.Float(152,130),
//	              new Point2D.Float(143,129),new Point2D.Float(140,131),new Point2D.Float(129,136),new Point2D.Float(126,139)};
//			points = Arrays.asList(point3_array);
//
//			break;
//
//		case CHECK:
//			Point2D.Float[] point4_array = {
//				 new Point2D.Float(91,185),new Point2D.Float(93,185),new Point2D.Float(95,185),new Point2D.Float(97,185),new Point2D.Float(100,188),
//	             new Point2D.Float(102,189),new Point2D.Float(104,190),new Point2D.Float(106,193),new Point2D.Float(108,195),new Point2D.Float(110,198),
//	             new Point2D.Float(112,201),new Point2D.Float(114,204),new Point2D.Float(115,207),new Point2D.Float(117,210),new Point2D.Float(118,212),
//	             new Point2D.Float(120,214),new Point2D.Float(121,217),new Point2D.Float(122,219),new Point2D.Float(123,222),new Point2D.Float(124,224),
//	             new Point2D.Float(126,226),new Point2D.Float(127,229),new Point2D.Float(129,231),new Point2D.Float(130,233),new Point2D.Float(129,231),
//	             new Point2D.Float(129,228),new Point2D.Float(129,226),new Point2D.Float(129,224),new Point2D.Float(129,221),new Point2D.Float(129,218),
//	             new Point2D.Float(129,212),new Point2D.Float(129,208),new Point2D.Float(130,198),new Point2D.Float(132,189),new Point2D.Float(134,182),
//	             new Point2D.Float(137,173),new Point2D.Float(143,164),new Point2D.Float(147,157),new Point2D.Float(151,151),new Point2D.Float(155,144),
//	             new Point2D.Float(161,137),new Point2D.Float(165,131),new Point2D.Float(171,122),new Point2D.Float(174,118),new Point2D.Float(176,114),
//	             new Point2D.Float(177,112),new Point2D.Float(177,114),new Point2D.Float(175,116),new Point2D.Float(173,118) };
//			points = Arrays.asList(point4_array);
//;
//			break;
//
//		case CARET:
//			Point2D.Float[] point5_array = {
//				  new Point2D.Float(79,245),new Point2D.Float(79,242),new Point2D.Float(79,239),new Point2D.Float(80,237),new Point2D.Float(80,234),
//	              new Point2D.Float(81,232),new Point2D.Float(82,230),new Point2D.Float(84,224),new Point2D.Float(86,220),new Point2D.Float(86,218),
//	              new Point2D.Float(87,216),new Point2D.Float(88,213),new Point2D.Float(90,207),new Point2D.Float(91,202),new Point2D.Float(92,200),
//	              new Point2D.Float(93,194),new Point2D.Float(94,192),new Point2D.Float(96,189),new Point2D.Float(97,186),new Point2D.Float(100,179),
//	              new Point2D.Float(102,173),new Point2D.Float(105,165),new Point2D.Float(107,160),new Point2D.Float(109,158),new Point2D.Float(112,151),
//	              new Point2D.Float(115,144),new Point2D.Float(117,139),new Point2D.Float(119,136),new Point2D.Float(119,134),new Point2D.Float(120,132),
//	              new Point2D.Float(121,129),new Point2D.Float(122,127),new Point2D.Float(124,125),new Point2D.Float(126,124),new Point2D.Float(129,125),
//	              new Point2D.Float(131,127),new Point2D.Float(132,130),new Point2D.Float(136,139),new Point2D.Float(141,154),new Point2D.Float(145,166),
//	              new Point2D.Float(151,182),new Point2D.Float(156,193),new Point2D.Float(157,196),new Point2D.Float(161,209),new Point2D.Float(162,211),
//	              new Point2D.Float(167,223),new Point2D.Float(169,229),new Point2D.Float(170,231),new Point2D.Float(173,237),new Point2D.Float(176,242),
//	              new Point2D.Float(177,244),new Point2D.Float(179,250),new Point2D.Float(181,255),new Point2D.Float(182,257) };
//			points = Arrays.asList(point5_array);
//
//			break;
//
//		case QUESTION:
//			Point2D.Float[] point6_array = {
//				  new Point2D.Float(104,145),new Point2D.Float(103,142),new Point2D.Float(103,140),new Point2D.Float(103,138),new Point2D.Float(103,135),
//	              new Point2D.Float(104,133),new Point2D.Float(105,131),new Point2D.Float(106,128),new Point2D.Float(107,125),new Point2D.Float(108,123),
//	              new Point2D.Float(111,121),new Point2D.Float(113,118),new Point2D.Float(115,116),new Point2D.Float(117,116),new Point2D.Float(119,116),
//	              new Point2D.Float(121,115),new Point2D.Float(124,116),new Point2D.Float(126,115),new Point2D.Float(128,114),new Point2D.Float(130,115),
//	              new Point2D.Float(133,116),new Point2D.Float(135,117),new Point2D.Float(140,120),new Point2D.Float(142,121),new Point2D.Float(144,123),
//	              new Point2D.Float(146,125),new Point2D.Float(149,127),new Point2D.Float(150,129),new Point2D.Float(152,130),new Point2D.Float(154,132),
//	              new Point2D.Float(156,134),new Point2D.Float(158,137),new Point2D.Float(159,139),new Point2D.Float(160,141),new Point2D.Float(160,143),
//	              new Point2D.Float(160,146),new Point2D.Float(160,149),new Point2D.Float(159,153),new Point2D.Float(158,155),new Point2D.Float(157,157),
//	              new Point2D.Float(155,159),new Point2D.Float(153,161),new Point2D.Float(151,163),new Point2D.Float(146,167),new Point2D.Float(142,170),
//	              new Point2D.Float(138,172),new Point2D.Float(134,173),new Point2D.Float(132,175),new Point2D.Float(127,175),new Point2D.Float(124,175),
//	              new Point2D.Float(122,176),new Point2D.Float(120,178),new Point2D.Float(119,180),new Point2D.Float(119,183),new Point2D.Float(119,185),
//	              new Point2D.Float(120,190),new Point2D.Float(121,194),new Point2D.Float(122,200),new Point2D.Float(123,205),new Point2D.Float(123,211),
//	              new Point2D.Float(124,215),new Point2D.Float(124,223),new Point2D.Float(124,225)};
//			points = Arrays.asList(point6_array);
//			break;
//
//
//		case ARROW:
//			Point2D.Float[] point7_array = {
//					new Point2D.Float(68,222),new Point2D.Float(70,220),new Point2D.Float(73,218),new Point2D.Float(75,217),
//					new Point2D.Float(77,215),new Point2D.Float(80,213),new Point2D.Float(82,212),new Point2D.Float(84,210),
//					new Point2D.Float(87,209),new Point2D.Float(89,208),new Point2D.Float(92,206),new Point2D.Float(95,204),
//					new Point2D.Float(101,201),new Point2D.Float(106,198),new Point2D.Float(112,194),new Point2D.Float(118,191),
//					new Point2D.Float(124,187),new Point2D.Float(127,186),new Point2D.Float(132,183),new Point2D.Float(138,181),
//					new Point2D.Float(141,180),new Point2D.Float(146,178),new Point2D.Float(154,173),new Point2D.Float(159,171),
//					new Point2D.Float(161,170),new Point2D.Float(166,167),new Point2D.Float(168,167),new Point2D.Float(171,166),
//					new Point2D.Float(174,164),new Point2D.Float(177,162),new Point2D.Float(180,160),new Point2D.Float(182,158),
//					new Point2D.Float(183,156),new Point2D.Float(181,154),new Point2D.Float(178,153),new Point2D.Float(171,153),
//					new Point2D.Float(164,153),new Point2D.Float(160,153),new Point2D.Float(150,154),new Point2D.Float(147,155),
//					new Point2D.Float(141,157),new Point2D.Float(137,158),new Point2D.Float(135,158),new Point2D.Float(137,158),
//					new Point2D.Float(140,157),new Point2D.Float(143,156),new Point2D.Float(151,154),new Point2D.Float(160,152),
//					new Point2D.Float(170,149),new Point2D.Float(179,147),new Point2D.Float(185,145),new Point2D.Float(192,144),
//					new Point2D.Float(196,144),new Point2D.Float(198,144),new Point2D.Float(200,144),new Point2D.Float(201,147),
//					new Point2D.Float(199,149),new Point2D.Float(194,157),new Point2D.Float(191,160),new Point2D.Float(186,167),
//					new Point2D.Float(180,176),new Point2D.Float(177,179),new Point2D.Float(171,187),new Point2D.Float(169,189),
//					new Point2D.Float(165,194),new Point2D.Float(164,196)};
//			points = Arrays.asList(point7_array);
//
//			break;
//
//		case LEFTSQUAREBRACKET:
//			Point2D.Float[] point8_array = {
//					new Point2D.Float(140,124),new Point2D.Float(138,123),new Point2D.Float(135,122),new Point2D.Float(133,123),
//					new Point2D.Float(130,123),new Point2D.Float(128,124),new Point2D.Float(125,125),new Point2D.Float(122,124),
//					new Point2D.Float(120,124),new Point2D.Float(118,124),new Point2D.Float(116,125),new Point2D.Float(113,125),
//					new Point2D.Float(111,125),new Point2D.Float(108,124),new Point2D.Float(106,125),new Point2D.Float(104,125),
//					new Point2D.Float(102,124),new Point2D.Float(100,123),new Point2D.Float(98,123),new Point2D.Float(95,124),
//					new Point2D.Float(93,123),new Point2D.Float(90,124),new Point2D.Float(88,124),new Point2D.Float(85,125),
//					new Point2D.Float(83,126),new Point2D.Float(81,127),new Point2D.Float(81,129),new Point2D.Float(82,131),
//					new Point2D.Float(82,134),new Point2D.Float(83,138),new Point2D.Float(84,141),new Point2D.Float(84,144),
//					new Point2D.Float(85,148),new Point2D.Float(85,151),new Point2D.Float(86,156),new Point2D.Float(86,160),
//					new Point2D.Float(86,164),new Point2D.Float(86,168),new Point2D.Float(87,171),new Point2D.Float(87,175),
//					new Point2D.Float(87,179),new Point2D.Float(87,182),new Point2D.Float(87,186),new Point2D.Float(88,188),
//					new Point2D.Float(88,195),new Point2D.Float(88,198),new Point2D.Float(88,201),new Point2D.Float(88,207),
//					new Point2D.Float(89,211),new Point2D.Float(89,213),new Point2D.Float(89,217),new Point2D.Float(89,222),
//					new Point2D.Float(88,225),new Point2D.Float(88,229),new Point2D.Float(88,231),new Point2D.Float(88,233),
//					new Point2D.Float(88,235),new Point2D.Float(89,237),new Point2D.Float(89,240),new Point2D.Float(89,242),
//					new Point2D.Float(91,241),new Point2D.Float(94,241),new Point2D.Float(96,240),new Point2D.Float(98,239),
//					new Point2D.Float(105,240),new Point2D.Float(109,240),new Point2D.Float(113,239),new Point2D.Float(116,240),
//					new Point2D.Float(121,239),new Point2D.Float(130,240),new Point2D.Float(136,237),new Point2D.Float(139,237),
//					new Point2D.Float(144,238),new Point2D.Float(151,237),new Point2D.Float(157,236),new Point2D.Float(159,237)};
//			points = Arrays.asList(point8_array);
//
//			break;
//
//		case RIGHTSQUAREBRACKET:
//			Point2D.Float[] point9_array = {
//					new Point2D.Float(112,138),new Point2D.Float(112,136),new Point2D.Float(115,136),new Point2D.Float(118,137),
//					new Point2D.Float(120,136),new Point2D.Float(123,136),new Point2D.Float(125,136),new Point2D.Float(128,136),
//					new Point2D.Float(131,136),new Point2D.Float(134,135),new Point2D.Float(137,135),new Point2D.Float(140,134),
//					new Point2D.Float(143,133),new Point2D.Float(145,132),new Point2D.Float(147,132),new Point2D.Float(149,132),
//					new Point2D.Float(152,132),new Point2D.Float(153,134),new Point2D.Float(154,137),new Point2D.Float(155,141),
//					new Point2D.Float(156,144),new Point2D.Float(157,152),new Point2D.Float(158,161),new Point2D.Float(160,170),
//					new Point2D.Float(162,182),new Point2D.Float(164,192),new Point2D.Float(166,200),new Point2D.Float(167,209),
//					new Point2D.Float(168,214),new Point2D.Float(168,216),new Point2D.Float(169,221),new Point2D.Float(169,223),
//					new Point2D.Float(169,228),new Point2D.Float(169,231),new Point2D.Float(166,233),new Point2D.Float(164,234),
//					new Point2D.Float(161,235),new Point2D.Float(155,236),new Point2D.Float(147,235),new Point2D.Float(140,233),
//					new Point2D.Float(131,233),new Point2D.Float(124,233),new Point2D.Float(117,235),new Point2D.Float(114,238),
//					new Point2D.Float(112,238)};
//			points = Arrays.asList(point9_array);
//
//			break;
//
//
//		case V:
//			Point2D.Float[] point10_array = {
//					new Point2D.Float(89,164),new Point2D.Float(90,162),new Point2D.Float(92,162),new Point2D.Float(94,164),
//					new Point2D.Float(95,166),new Point2D.Float(96,169),new Point2D.Float(97,171),new Point2D.Float(99,175),
//					new Point2D.Float(101,178),new Point2D.Float(103,182),new Point2D.Float(106,189),new Point2D.Float(108,194),
//					new Point2D.Float(111,199),new Point2D.Float(114,204),new Point2D.Float(117,209),new Point2D.Float(119,214),
//					new Point2D.Float(122,218),new Point2D.Float(124,222),new Point2D.Float(126,225),new Point2D.Float(128,228),
//					new Point2D.Float(130,229),new Point2D.Float(133,233),new Point2D.Float(134,236),new Point2D.Float(136,239),
//					new Point2D.Float(138,240),new Point2D.Float(139,242),new Point2D.Float(140,244),new Point2D.Float(142,242),
//					new Point2D.Float(142,240),new Point2D.Float(142,237),new Point2D.Float(143,235),new Point2D.Float(143,233),
//					new Point2D.Float(145,229),new Point2D.Float(146,226),new Point2D.Float(148,217),new Point2D.Float(149,208),
//					new Point2D.Float(149,205),new Point2D.Float(151,196),new Point2D.Float(151,193),new Point2D.Float(153,182),
//					new Point2D.Float(155,172),new Point2D.Float(157,165),new Point2D.Float(159,160),new Point2D.Float(162,155),
//					new Point2D.Float(164,150),new Point2D.Float(165,148),new Point2D.Float(166,146)};
//			points = Arrays.asList(point10_array);
//
//			break;
//
//		case DELETE:
//			Point2D.Float[] point11_array = {
//					new Point2D.Float(123,129),new Point2D.Float(123,131),new Point2D.Float(124,133),new Point2D.Float(125,136),
//					new Point2D.Float(127,140),new Point2D.Float(129,142),new Point2D.Float(133,148),new Point2D.Float(137,154),
//					new Point2D.Float(143,158),new Point2D.Float(145,161),new Point2D.Float(148,164),new Point2D.Float(153,170),
//					new Point2D.Float(158,176),new Point2D.Float(160,178),new Point2D.Float(164,183),new Point2D.Float(168,188),
//					new Point2D.Float(171,191),new Point2D.Float(175,196),new Point2D.Float(178,200),new Point2D.Float(180,202),
//					new Point2D.Float(181,205),new Point2D.Float(184,208),new Point2D.Float(186,210),new Point2D.Float(187,213),
//					new Point2D.Float(188,215),new Point2D.Float(186,212),new Point2D.Float(183,211),new Point2D.Float(177,208),
//					new Point2D.Float(169,206),new Point2D.Float(162,205),new Point2D.Float(154,207),new Point2D.Float(145,209),
//					new Point2D.Float(137,210),new Point2D.Float(129,214),new Point2D.Float(122,217),new Point2D.Float(118,218),
//					new Point2D.Float(111,221),new Point2D.Float(109,222),new Point2D.Float(110,219),new Point2D.Float(112,217),
//					new Point2D.Float(118,209),new Point2D.Float(120,207),new Point2D.Float(128,196),new Point2D.Float(135,187),
//					new Point2D.Float(138,183),new Point2D.Float(148,167),new Point2D.Float(157,153),new Point2D.Float(163,145),
//					new Point2D.Float(165,142),new Point2D.Float(172,133),new Point2D.Float(177,127),new Point2D.Float(179,127),
//					new Point2D.Float(180,125)};
//			points = Arrays.asList(point11_array);
//
//			break;
//
//		case LEFTCURLYBRACE:
//			Point2D.Float[] point12_array = {
//					new Point2D.Float(150,116),new Point2D.Float(147,117),new Point2D.Float(145,116),new Point2D.Float(142,116),
//					new Point2D.Float(139,117),new Point2D.Float(136,117),new Point2D.Float(133,118),new Point2D.Float(129,121),
//					new Point2D.Float(126,122),new Point2D.Float(123,123),new Point2D.Float(120,125),new Point2D.Float(118,127),
//					new Point2D.Float(115,128),new Point2D.Float(113,129),new Point2D.Float(112,131),new Point2D.Float(113,134),
//					new Point2D.Float(115,134),new Point2D.Float(117,135),new Point2D.Float(120,135),new Point2D.Float(123,137),
//					new Point2D.Float(126,138),new Point2D.Float(129,140),new Point2D.Float(135,143),new Point2D.Float(137,144),
//					new Point2D.Float(139,147),new Point2D.Float(141,149),new Point2D.Float(140,152),new Point2D.Float(139,155),
//					new Point2D.Float(134,159),new Point2D.Float(131,161),new Point2D.Float(124,166),new Point2D.Float(121,166),
//					new Point2D.Float(117,166),new Point2D.Float(114,167),new Point2D.Float(112,166),new Point2D.Float(114,164),
//					new Point2D.Float(116,163),new Point2D.Float(118,163),new Point2D.Float(120,162),new Point2D.Float(122,163),
//					new Point2D.Float(125,164),new Point2D.Float(127,165),new Point2D.Float(129,166),new Point2D.Float(130,168),
//					new Point2D.Float(129,171),new Point2D.Float(127,175),new Point2D.Float(125,179),new Point2D.Float(123,184),
//					new Point2D.Float(121,190),new Point2D.Float(120,194),new Point2D.Float(119,199),new Point2D.Float(120,202),
//					new Point2D.Float(123,207),new Point2D.Float(127,211),new Point2D.Float(133,215),new Point2D.Float(142,219),
//					new Point2D.Float(148,220),new Point2D.Float(151,221)};
//			points = Arrays.asList(point12_array);
//
//			break;
//
//		case RIGHTCURLYBRACE:
//			Point2D.Float[] point13_array = {
//					new Point2D.Float(117,132),new Point2D.Float(115,132),new Point2D.Float(115,129),new Point2D.Float(117,129),
//					new Point2D.Float(119,128),new Point2D.Float(122,127),new Point2D.Float(125,127),new Point2D.Float(127,127),
//					new Point2D.Float(130,127),new Point2D.Float(133,129),new Point2D.Float(136,129),new Point2D.Float(138,130),
//					new Point2D.Float(140,131),new Point2D.Float(143,134),new Point2D.Float(144,136),new Point2D.Float(145,139),
//					new Point2D.Float(145,142),new Point2D.Float(145,145),new Point2D.Float(145,147),new Point2D.Float(145,149),
//					new Point2D.Float(144,152),new Point2D.Float(142,157),new Point2D.Float(141,160),new Point2D.Float(139,163),
//					new Point2D.Float(137,166),new Point2D.Float(135,167),new Point2D.Float(133,169),new Point2D.Float(131,172),
//					new Point2D.Float(128,173),new Point2D.Float(126,176),new Point2D.Float(125,178),new Point2D.Float(125,180),
//					new Point2D.Float(125,182),new Point2D.Float(126,184),new Point2D.Float(128,187),new Point2D.Float(130,187),
//					new Point2D.Float(132,188),new Point2D.Float(135,189),new Point2D.Float(140,189),new Point2D.Float(145,189),
//					new Point2D.Float(150,187),new Point2D.Float(155,186),new Point2D.Float(157,185),new Point2D.Float(159,184),
//					new Point2D.Float(156,185),new Point2D.Float(154,185),new Point2D.Float(149,185),new Point2D.Float(145,187),
//					new Point2D.Float(141,188),new Point2D.Float(136,191),new Point2D.Float(134,191),new Point2D.Float(131,192),
//					new Point2D.Float(129,193),new Point2D.Float(129,195),new Point2D.Float(129,197),new Point2D.Float(131,200),
//					new Point2D.Float(133,202),new Point2D.Float(136,206),new Point2D.Float(139,211),new Point2D.Float(142,215),
//					new Point2D.Float(145,220),new Point2D.Float(147,225),new Point2D.Float(148,231),new Point2D.Float(147,239),
//					new Point2D.Float(144,244),new Point2D.Float(139,248),new Point2D.Float(134,250),new Point2D.Float(126,253),
//					new Point2D.Float(119,253),new Point2D.Float(115,253)};
//			points = Arrays.asList(point13_array);
//
//			break;
//
//		case STAR:
//			Point2D.Float[] point14_array = {
//					new Point2D.Float(75,250),new Point2D.Float(75,247),new Point2D.Float(77,244),new Point2D.Float(78,242),
//					new Point2D.Float(79,239),new Point2D.Float(80,237),new Point2D.Float(82,234),new Point2D.Float(82,232),
//					new Point2D.Float(84,229),new Point2D.Float(85,225),new Point2D.Float(87,222),new Point2D.Float(88,219),
//					new Point2D.Float(89,216),new Point2D.Float(91,212),new Point2D.Float(92,208),new Point2D.Float(94,204),
//					new Point2D.Float(95,201),new Point2D.Float(96,196),new Point2D.Float(97,194),new Point2D.Float(98,191),
//					new Point2D.Float(100,185),new Point2D.Float(102,178),new Point2D.Float(104,173),new Point2D.Float(104,171),
//					new Point2D.Float(105,164),new Point2D.Float(106,158),new Point2D.Float(107,156),new Point2D.Float(107,152),
//					new Point2D.Float(108,145),new Point2D.Float(109,141),new Point2D.Float(110,139),new Point2D.Float(112,133),
//					new Point2D.Float(113,131),new Point2D.Float(116,127),new Point2D.Float(117,125),new Point2D.Float(119,122),
//					new Point2D.Float(121,121),new Point2D.Float(123,120),new Point2D.Float(125,122),new Point2D.Float(125,125),
//					new Point2D.Float(127,130),new Point2D.Float(128,133),new Point2D.Float(131,143),new Point2D.Float(136,153),
//					new Point2D.Float(140,163),new Point2D.Float(144,172),new Point2D.Float(145,175),new Point2D.Float(151,189),
//					new Point2D.Float(156,201),new Point2D.Float(161,213),new Point2D.Float(166,225),new Point2D.Float(169,233),
//					new Point2D.Float(171,236),new Point2D.Float(174,243),new Point2D.Float(177,247),new Point2D.Float(178,249),
//					new Point2D.Float(179,251),new Point2D.Float(180,253),new Point2D.Float(180,255),new Point2D.Float(179,257),
//					new Point2D.Float(177,257),new Point2D.Float(174,255),new Point2D.Float(169,250),new Point2D.Float(164,247),
//					new Point2D.Float(160,245),new Point2D.Float(149,238),new Point2D.Float(138,230),new Point2D.Float(127,221),
//					new Point2D.Float(124,220),new Point2D.Float(112,212),new Point2D.Float(110,210),new Point2D.Float(96,201),
//					new Point2D.Float(84,195),new Point2D.Float(74,190),new Point2D.Float(64,182),new Point2D.Float(55,175),
//					new Point2D.Float(51,172),new Point2D.Float(49,170),new Point2D.Float(51,169),new Point2D.Float(56,169),
//					new Point2D.Float(66,169),new Point2D.Float(78,168),new Point2D.Float(92,166),new Point2D.Float(107,164),
//					new Point2D.Float(123,161),new Point2D.Float(140,162),new Point2D.Float(156,162),new Point2D.Float(171,160),
//					new Point2D.Float(173,160),new Point2D.Float(186,160),new Point2D.Float(195,160),new Point2D.Float(198,161),
//					new Point2D.Float(203,163),new Point2D.Float(208,163),new Point2D.Float(206,164),new Point2D.Float(200,167),
//					new Point2D.Float(187,172),new Point2D.Float(174,179),new Point2D.Float(172,181),new Point2D.Float(153,192),
//					new Point2D.Float(137,201),new Point2D.Float(123,211),new Point2D.Float(112,220),new Point2D.Float(99,229),
//					new Point2D.Float(90,237),new Point2D.Float(80,244),new Point2D.Float(73,250),new Point2D.Float(69,254),
//					new Point2D.Float(69,252)};
//			points = Arrays.asList(point14_array);
//
//			break;
//		case PIGTAIL:
//			Point2D.Float[] point15_array = {
//					new Point2D.Float(81,219),new Point2D.Float(84,218),new Point2D.Float(86,220),new Point2D.Float(88,220),
//					new Point2D.Float(90,220),new Point2D.Float(92,219),new Point2D.Float(95,220),new Point2D.Float(97,219),
//					new Point2D.Float(99,220),new Point2D.Float(102,218),new Point2D.Float(105,217),new Point2D.Float(107,216),
//					new Point2D.Float(110,216),new Point2D.Float(113,214),new Point2D.Float(116,212),new Point2D.Float(118,210),
//					new Point2D.Float(121,208),new Point2D.Float(124,205),new Point2D.Float(126,202),new Point2D.Float(129,199),
//					new Point2D.Float(132,196),new Point2D.Float(136,191),new Point2D.Float(139,187),new Point2D.Float(142,182),
//					new Point2D.Float(144,179),new Point2D.Float(146,174),new Point2D.Float(148,170),new Point2D.Float(149,168),
//					new Point2D.Float(151,162),new Point2D.Float(152,160),new Point2D.Float(152,157),new Point2D.Float(152,155),
//					new Point2D.Float(152,151),new Point2D.Float(152,149),new Point2D.Float(152,146),new Point2D.Float(149,142),
//					new Point2D.Float(148,139),new Point2D.Float(145,137),new Point2D.Float(141,135),new Point2D.Float(139,135),
//					new Point2D.Float(134,136),new Point2D.Float(130,140),new Point2D.Float(128,142),new Point2D.Float(126,145),
//					new Point2D.Float(122,150),new Point2D.Float(119,158),new Point2D.Float(117,163),new Point2D.Float(115,170),
//					new Point2D.Float(114,175),new Point2D.Float(117,184),new Point2D.Float(120,190),new Point2D.Float(125,199),
//					new Point2D.Float(129,203),new Point2D.Float(133,208),new Point2D.Float(138,213),new Point2D.Float(145,215),
//					new Point2D.Float(155,218),new Point2D.Float(164,219),new Point2D.Float(166,219),new Point2D.Float(177,219),
//					new Point2D.Float(182,218),new Point2D.Float(192,216),new Point2D.Float(196,213),new Point2D.Float(199,212),
//					new Point2D.Float(201,211)};
//			points = Arrays.asList(point15_array);
//			break;
//
//		case PACKAGE:
//			Point2D.Float[] point20_array = {
//					new Point2D.Float(332,174),new Point2D.Float(347,173),new Point2D.Float(363,171),new Point2D.Float(382,168),
//					new Point2D.Float(390,166),new Point2D.Float(405,163),new Point2D.Float(419,162),new Point2D.Float(421,172),
//					new Point2D.Float(422,186),new Point2D.Float(421,203),new Point2D.Float(419,213),new Point2D.Float(417,233),
//					new Point2D.Float(416,244),new Point2D.Float(413,261),new Point2D.Float(411,275),new Point2D.Float(413,283),
//					new Point2D.Float(427,284),new Point2D.Float(439,283),new Point2D.Float(447,284),new Point2D.Float(467,282),
//					new Point2D.Float(477,280),new Point2D.Float(493,279),new Point2D.Float(508,277),new Point2D.Float(520,277),
//					new Point2D.Float(525,284),new Point2D.Float(525,295),new Point2D.Float(522,315),new Point2D.Float(519,328),
//					new Point2D.Float(517,340),new Point2D.Float(516,354),new Point2D.Float(513,367),new Point2D.Float(511,385),
//					new Point2D.Float(509,397),new Point2D.Float(505,410),new Point2D.Float(502,424),new Point2D.Float(491,432),
//					new Point2D.Float(471,435),new Point2D.Float(453,434),new Point2D.Float(439,434),new Point2D.Float(422,433),
//					new Point2D.Float(400,433),new Point2D.Float(388,431),new Point2D.Float(367,430),new Point2D.Float(353,429),
//					new Point2D.Float(332,430),new Point2D.Float(317,427),new Point2D.Float(311,413),new Point2D.Float(309,390),
//					new Point2D.Float(308,386),new Point2D.Float(307,370),new Point2D.Float(308,352),new Point2D.Float(310,335),
//					new Point2D.Float(311,306),new Point2D.Float(312,289),new Point2D.Float(310,273),new Point2D.Float(309,259),
//					new Point2D.Float(309,243),new Point2D.Float(311,226),new Point2D.Float(315,209),new Point2D.Float(316,200),
//					new Point2D.Float(320,180),new Point2D.Float(323,164),new Point2D.Float(331,157),new Point2D.Float(332,158),
//			};
//			points = Arrays.asList(point20_array);
//			break;
//			
//
//		default: break;
//		}
//
//		templates.add(new Template(gesture, points, direction));
//	}
//}
