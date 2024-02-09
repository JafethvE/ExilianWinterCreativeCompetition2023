--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.0

-- Started on 2024-02-09 15:02:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3348 (class 0 OID 16386)
-- Dependencies: 216
-- Data for Name: animal; Type: TABLE DATA; Schema: public; Owner: animalapplication
--

INSERT INTO public.animal VALUES (1, 'Arctic ground squirrels have a compact body with short legs and a bushy tail.', 'Arctic Ground Squirrel');
INSERT INTO public.animal VALUES (2, 'Alpine marmots are large ground dwelling squirrels, with a thickset body and short legs.', 'Alpine Marmot');
INSERT INTO public.animal VALUES (3, 'Big brown bats are medium sized bats with a wingspan of 32 to 40 cm. ', 'Big Brown Bat');
INSERT INTO public.animal VALUES (4, 'Black bears are medium sized bears with a thick, black or dark brown fur. They have a broad head, a short tail, and a large body.', 'American Black Bear');
INSERT INTO public.animal VALUES (5, 'Brown bears are large bears with a hump on their shoulders and long, thick fur that ranges from light brown to dark brown in color.', 'Brown Bear');
INSERT INTO public.animal VALUES (6, 'Eastern chipmunks are small rodents with a body length of 20 to 30 cm and a bushy tail. They have a red brown coat with dark and light stripes on their back and head.', 'Eastern Chipmunk');
INSERT INTO public.animal VALUES (7, 'European hedgehogs are small mammals with a body length of 20 to 30 cm and a short tail. They have a coat of dense spines on their back and sides, with fur on their face and belly.', 'European Hedgehog');
INSERT INTO public.animal VALUES (8, 'Fat tailed dwarf lemurs are small primates with a body length of 20 to 28 cm and a tail that is almost equal in length. They have a gray or brown fur and large, round eyes. ', 'Fat Tailed Dwarf Lemur');
INSERT INTO public.animal VALUES (9, 'Garden dormice are small rodents with a body length of 10  to 15 cm and a bushy, bicolored tail. They have a gray or brown fur with a white belly and a characteristic black mask around their eyes.', 'Garden Dormouse');
INSERT INTO public.animal VALUES (10, 'Gray bats are medium sized bats with a wingspan of 28 to 33 cm. As the name suggests, their fur is gray. ', 'Gray Bat');
INSERT INTO public.animal VALUES (11, 'Greater horseshoe bats are medium sized bats with a wingspan of 34 to 40 cm. They have brown fur with a lighter colored belly and a horseshoe shaped noseleaf.', 'Greater Horseshoe Bat');
INSERT INTO public.animal VALUES (12, 'Groundhogs, also known as woodchucks, are large ground dwelling squirrels, with a thickset body and short legs. They have a coarse coat that is reddish brown to grayish brown in color.', 'Groundhog');
INSERT INTO public.animal VALUES (13, 'Hazel dormice are small rodents with a body length of 6 to 9 cm and a bushy tail. They have golden brown fur with a white belly.', 'Hazel Dormouse');
INSERT INTO public.animal VALUES (14, 'Hoary bats are large bats with a wingspan of 40 to 43 cm. They have dense, frosty gray fur with a lighter colored belly.', 'Hoary Bat');
INSERT INTO public.animal VALUES (15, 'Northern long eared bats are small bats with a wingspan of 23 to 26 cm. They have brown fur with a lighter colored belly and characteristic long ears', 'Northern Long Eared Bat');
INSERT INTO public.animal VALUES (16, 'Short beaked echidnas are small, egg laying mammals with a body length of 30 to 45 cm. They have a distinctive appearance with a body covered in spines and fur, a long snout, and a short tail.', 'Short Beaked Echidna');
INSERT INTO public.animal VALUES (17, 'Thirteen lined ground squirrels are small ground dwelling squirrels with a body length of 20 to 30 cm and a short tail. They have a coat that is tawny to light brown with 13 alternating dark and light stripes on their back.', 'Thirteen Lined Ground Squirrel');
INSERT INTO public.animal VALUES (18, 'Vancouver Island marmots are large ground dwelling squirrels with a thickset body and short legs. They have chocolate brown fur with a white patch on the nose and forehead.', 'Vancouver Island Marmot');
INSERT INTO public.animal VALUES (19, 'Whiskered bats are small bats with a wingspan of 21 to 25 cm. They have brown fur with a lighter colored belly and a distinctive fringe of hairs around the snout.', 'Whiskered Bat');
INSERT INTO public.animal VALUES (20, 'Yellow bellied marmots are large ground dwelling squirrels with a thickset body and short legs. They have a coat that is brown to reddish brown with a yellowish belly.', 'Yellow Bellied Marmot');


--
-- TOC entry 3354 (class 0 OID 0)
-- Dependencies: 215
-- Name: animal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: animalapplication
--

SELECT pg_catalog.setval('public.animal_id_seq', 20, true);


-- Completed on 2024-02-09 15:02:22

--
-- PostgreSQL database dump complete
--

