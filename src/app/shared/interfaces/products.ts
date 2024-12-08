export interface productAddData {
  name: string;
  description: string;
}

export interface productFilteredPaginated {
  page: number;
  pageSize: number;
  sortDirection: string;
  sortBy: string;
  name: string | '';
  category: {
    id: number | undefined;
    name: string | undefined;
  };
}
