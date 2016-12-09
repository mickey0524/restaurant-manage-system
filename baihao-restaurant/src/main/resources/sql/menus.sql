CREATE TABLE menu
(
    menu_name VARCHAR (255) PRIMARY KEY NOT NULL,
    menu_num VARCHAR(255) NOT NULL,
    menu_time VARCHAR (255) NOT NULL,
    menu_cost VARCHAR(255) NOT NULL
);

CREATE TABLE balance
(
    date VARCHAR (255) PRIMARY KEY NOT NULL,
    expense VARCHAR (255) NOT NULL,
    income VARCHAR (255) NOT NULL
);

CREATE TABLE indent
(
    table_num VARCHAR (255) PRIMARY KEY NOT NULL,
    bread_crumbs_soup VARCHAR (255) NOT NULL,
    delicious_carrot_bread VARCHAR (255) NOT NULL,
    yummy_noodles VARCHAR (255) NOT NULL,
    vegetable_pasta VARCHAR (255) NOT NULL,
    mixed_veg_salad VARCHAR (255) NOT NULL,
    breakFast_special VARCHAR (255) NOT NULL,
    healthy_tomato_soup VARCHAR (255) NOT NULL,
    decilious_sushi VARCHAR (255) NOT NULL,
    sum VARCHAR (255) NOT NULL
);